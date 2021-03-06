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

package com.microsoft.tooling.msservices.model;

public class Subscription {
    private String id;
    private String name;
    private String managementCertificate;
    private String serviceManagementUrl;
    private boolean selected;
    private String tenantId;
    private int maxStorageAccounts;
    private int maxHostedServices;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagementCertificate() {
        return managementCertificate;
    }

    public void setManagementCertificate(String managementCertificate) {
        this.managementCertificate = managementCertificate;
    }

    public String getServiceManagementUrl() {
        return serviceManagementUrl;
    }

    public void setServiceManagementUrl(String serviceManagementUrl) {
        this.serviceManagementUrl = serviceManagementUrl;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getMaxStorageAccounts() {
        return maxStorageAccounts;
    }

    public void setMaxStorageAccounts(int maxStorageAccounts) {
    	this.maxStorageAccounts = maxStorageAccounts;
    }

    public int getMaxHostedServices() {
    	return maxHostedServices;
    }

    public void setMaxHostedServices(int maxHostedServices) {
    	this.maxHostedServices = maxHostedServices;
    }
}
