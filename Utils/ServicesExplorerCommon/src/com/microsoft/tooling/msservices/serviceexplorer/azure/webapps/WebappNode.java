/**
 * Copyright (c) Microsoft Corporation
 * <p/>
 * All rights reserved.
 * <p/>
 * MIT License
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.microsoft.tooling.msservices.serviceexplorer.azure.webapps;

import java.util.List;

import com.microsoft.tooling.msservices.components.DefaultLoader;
import com.microsoft.tooling.msservices.helpers.NotNull;
import com.microsoft.tooling.msservices.helpers.azure.AzureCmdException;
import com.microsoft.tooling.msservices.helpers.azure.AzureManagerImpl;
import com.microsoft.tooling.msservices.helpers.azure.AzureManager;
import com.microsoft.tooling.msservices.model.ws.WebSite;
import com.microsoft.tooling.msservices.serviceexplorer.*;
import com.microsoft.tooling.msservices.serviceexplorer.azure.AzureNodeActionPromptListener;

public class WebappNode extends Node {
	private static final String ACTION_START = "Start";
	private static final String ACTION_STOP = "Stop";
	private static final String ACTION_RESTART = "Restart";
	private static final String WEB_RUN_ICON = "website.png";
	private static final String WEB_STOP_ICON = "stopWebsite.png";
	String runStatus = "Running";
	private WebSite webSite;

	public WebappNode(WebappsModule parent, WebSite webSite, String icon) {
		super(webSite.getName(), webSite.getName(), parent, icon, true);
		this.webSite = webSite;

		loadActions();
	}

	public WebSite getWebSite() {
		return webSite;
	}

	@Override
	public List<NodeAction> getNodeActions() {
		boolean running = runStatus.equals(webSite.getStatus());
		getNodeActionByName(ACTION_START).setEnabled(!running);
		getNodeActionByName(ACTION_STOP).setEnabled(running);
		getNodeActionByName(ACTION_RESTART).setEnabled(running);

		return super.getNodeActions();
	}

	@Override
	protected void loadActions() {
		addAction("Stop", WEB_STOP_ICON, new NodeActionListener() {
			@Override
			public void actionPerformed(NodeActionEvent e) {
				DefaultLoader.getIdeHelper().runInBackground(null, "Stopping Web App", false, true, "Stopping Web App...", new Runnable() {
					@Override
					public void run() {
						try {
							AzureManager azureManager = AzureManagerImpl.getManager(getProject());
							azureManager.stopWebSite(webSite.getSubscriptionId(), webSite.getWebSpaceName(), webSite.getName());
							webSite = azureManager.getWebSite(webSite.getSubscriptionId(), webSite.getWebSpaceName(), webSite.getName());
							setIconPath(WEB_STOP_ICON);
						} catch (AzureCmdException e) {
							DefaultLoader.getUIHelper().showException("An error occurred while attempting to stop the Web App", e,
									"Azure Services Explorer - Error Stopping Web App", false, true);
						}
					}
				});
			}
		});
		addAction("Start", new NodeActionListener() {
			@Override
			public void actionPerformed(NodeActionEvent e) {
				DefaultLoader.getIdeHelper().runInBackground(null, "Starting Web App", false, true, "Starting Web App...", new Runnable() {
					@Override
					public void run() {
						try {
							AzureManager azureManager = AzureManagerImpl.getManager(getProject());
							azureManager.startWebSite(webSite.getSubscriptionId(), webSite.getWebSpaceName(), webSite.getName());
							webSite = azureManager.getWebSite(webSite.getSubscriptionId(), webSite.getWebSpaceName(), webSite.getName());
							setIconPath(WEB_RUN_ICON);
						} catch (AzureCmdException e) {
							DefaultLoader.getUIHelper().showException("An error occurred while attempting to start the Web App", e,
									"Azure Services Explorer - Error Starting Web App", false, true);
						}
					}
				});
			}
		});
		addAction(ACTION_RESTART, new NodeActionListener() {
			@Override
			public void actionPerformed(NodeActionEvent e) {
				DefaultLoader.getIdeHelper().runInBackground(null, "Restarting Web App", false, true, "Restarting Web App...", new Runnable() {
					@Override
					public void run() {
						try {
							AzureManager azureManager = AzureManagerImpl.getManager(getProject());
							azureManager.restartWebSite(webSite.getSubscriptionId(), webSite.getWebSpaceName(), webSite.getName());
							webSite = azureManager.getWebSite(webSite.getSubscriptionId(), webSite.getWebSpaceName(), webSite.getName());
							setIconPath(WEB_RUN_ICON);
						} catch (AzureCmdException e) {
							DefaultLoader.getUIHelper().showException("An error occurred while attempting to restart the Web App", e,
									"Azure Services Explorer - Error Restarting Web App", false, true);
						}
					}
				});
			}
		});
		addAction("Delete",new DeleteWebAppAction());
		super.loadActions();
	}

	private class DeleteWebAppAction extends AzureNodeActionPromptListener {
		DeleteWebAppAction() {
			super(WebappNode.this,
					String.format("This operation will delete Web App %s.\nAre you sure you want to continue?", webSite.getName()),
					"Deleting Web App");
		}

		@Override
		protected void azureNodeAction(NodeActionEvent e, @NotNull EventHelper.EventStateHandle stateHandle) throws AzureCmdException {
			try {
				AzureManagerImpl.getManager(getProject()).deleteWebSite(webSite.getSubscriptionId(), webSite.getWebSpaceName(), webSite.getName());
				DefaultLoader.getIdeHelper().invokeLater(new Runnable() {
					@Override
					public void run() {
						// instruct parent node to remove this node
						getParent().removeDirectChildNode(WebappNode.this);
					}
				});
			} catch (AzureCmdException ex) {
				DefaultLoader.getUIHelper().showException("An error occurred while attempting to delete the Web App", ex,
									"Azure Services Explorer - Error Deleting Web App", false, true);
			}
		}

		@Override
		protected void onSubscriptionsChanged(NodeActionEvent e) throws AzureCmdException {
		}
	}
}