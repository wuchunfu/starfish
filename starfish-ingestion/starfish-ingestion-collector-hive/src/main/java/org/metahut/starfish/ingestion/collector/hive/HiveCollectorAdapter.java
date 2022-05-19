package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.CollectorException;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollectorAdapter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.RetryingMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.MetaException;

import java.util.Objects;

public class HiveCollectorAdapter implements ICollectorAdapter {
    private final IMetaStoreClient metaStoreClient;

    public HiveCollectorAdapter(HiveCollectorAdapterParameter parameter) {
        Configuration conf = new Configuration();
        conf.set("hive.metastore.uris", parameter.getHiveMetastoreUris());
        try {
            metaStoreClient = RetryingMetaStoreClient.getProxy(conf, false);
        } catch (MetaException e) {
            throw new CollectorException("hive create meta store client exception", e);
        }
    }

    @Override
    public CollectorResult testConnection() {
        return Objects.isNull(metaStoreClient) ? new CollectorResult(false, "hive meta store client is null") : new CollectorResult(true);
    }

    @Override
    public IMetaStoreClient getMetaClient() {
        return metaStoreClient;
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(metaStoreClient)) {
            metaStoreClient.close();
        }
    }
}
