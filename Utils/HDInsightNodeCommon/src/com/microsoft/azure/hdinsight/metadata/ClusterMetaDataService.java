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
package com.microsoft.azure.hdinsight.metadata;

import com.microsoft.azure.hdinsight.sdk.cluster.IClusterDetail;
import com.microsoft.tooling.msservices.helpers.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ClusterMetaDataService {
    private static ClusterMetaDataService instance = new ClusterMetaDataService();
    private List<IClusterDetail> cachedClusters = new ArrayList<>();
//    private Map<Project, List<IClusterDetail>> cachedClustersMap = new HashMap<>();

    private ClusterMetaDataService() {
    }

    public static ClusterMetaDataService getInstance() {
        return instance;
    }

    public List<IClusterDetail> getCachedClusterDetails () {
        return cachedClusters;
    }

    public void addCachedClusters(@NotNull List<IClusterDetail> clusterDetails) {
        cachedClusters = clusterDetails;
    }

    public boolean isCachedClusterExist(@NotNull IClusterDetail clusterDetail) {
        for (IClusterDetail iClusterDetail : cachedClusters) {
            if (iClusterDetail.getName().equalsIgnoreCase(clusterDetail.getName())) {
                return true;
            }
        }
        return false;
    }
}
