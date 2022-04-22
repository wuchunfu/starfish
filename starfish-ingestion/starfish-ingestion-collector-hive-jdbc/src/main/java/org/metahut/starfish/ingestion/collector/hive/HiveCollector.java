package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.datasource.hive.HiveDatasource;
import org.metahut.starfish.datasource.hive.HiveDatasourceManager;
import org.metahut.starfish.ingestion.collector.api.CollectorResult;
import org.metahut.starfish.ingestion.collector.api.ICollector;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.IMessageProducer;

import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public class HiveCollector implements ICollector {

    private final HiveCollectorParameter hiveParameter;
    private final IMessageProducer producer;
    private final HiveDatasource hiveDatasource;
    private final IMetaStoreClient metaClient;

    public HiveCollector(HiveCollectorParameter hiveParameter) {
        this.hiveParameter = hiveParameter;
        producer = MetaMessageProducer.getInstance();
        hiveDatasource = new HiveDatasourceManager()
            .generateInstance(this.hiveParameter.getDatasourceParameter());
        metaClient = hiveDatasource.getMetaClient();
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(producer)) {
            producer.close();
        }

        if (Objects.nonNull(hiveDatasource)) {
            hiveDatasource.close();
        }
    }

    @Override
    public CollectorResult execute() {
        CollectorResult collectorResult = new CollectorResult();
        //transform the message
        List<Map<String, Object>> hiveMessages = getTables();
        try {
            producer
                .send(this.hiveParameter.getDatasourceId(), JSONUtils.toJSONString(hiveMessages));
        } catch (Exception e) {
            collectorResult.setState(false);
            collectorResult.setMessage(e.getMessage());
            return collectorResult;
        }
        collectorResult.setState(true);
        collectorResult.setMessage("send hive messages is success");
        return collectorResult;
    }

    //table metaData
    public List<Map<String, Object>> getTables() {

        Map<String, List<Table>> dataBase = new HashMap<>();
        try {
            metaClient.getAllDatabases().stream().forEach(dbName -> {
                try {
                    List<Table> tables = new ArrayList<>();
                    metaClient.getAllTables(dbName).forEach(tbName -> {
                        try {
                            tables.add(metaClient.getTable(dbName, tbName));
                        } catch (TException e) {
                            e.printStackTrace();
                        }
                    });
                    dataBase.put(dbName, tables);
                } catch (TException e) {
                    e.printStackTrace();
                }
            });
        } catch (TException e) {
            e.printStackTrace();
        }
        return dataBase.entrySet().stream().map(Entry::getValue
        ).flatMap(Collection::stream).collect(Collectors.toList()).stream().map(table -> {
            HashMap<String, Object> hiveCollectorTransformMessageDTO = new HashMap();
            hiveCollectorTransformMessageDTO.put("tableName", table.getTableName());
            hiveCollectorTransformMessageDTO.put("tableType", table.getTableType());
            hiveCollectorTransformMessageDTO.put("dbName", table.getDbName());
            hiveCollectorTransformMessageDTO.put("owner", table.getOwner());
            hiveCollectorTransformMessageDTO.put("creatime", table.getCreateTime());
            hiveCollectorTransformMessageDTO.put("lastAccessTime", table.getLastAccessTime());
            hiveCollectorTransformMessageDTO
                .put("totalSize", table.getParameters().get("totalSize"));
            hiveCollectorTransformMessageDTO.put("rowSize", table.getParameters().get("numRows"));
            hiveCollectorTransformMessageDTO.put("fieldSchema", table.getSd().getCols());
            hiveCollectorTransformMessageDTO.put("location", table.getSd().getLocation());
            hiveCollectorTransformMessageDTO.put("inputFormat", table.getSd().getInputFormat());
            hiveCollectorTransformMessageDTO.put("outputFormat", table.getSd().getOutputFormat());
            hiveCollectorTransformMessageDTO.put("compressed", table.getSd().isCompressed());
            hiveCollectorTransformMessageDTO.put("numBuckets", table.getSd().getNumBuckets());
            hiveCollectorTransformMessageDTO
                .put("partitionStatus", table.getPartitionKeys().size() > 0 ? true : false);
            hiveCollectorTransformMessageDTO.put("partitionKeys",
                Objects.nonNull(table.getPartitionKeys()) ? table.getPartitionKeys()
                    : new ArrayList<>());
            hiveCollectorTransformMessageDTO.put("ownerType", table.getOwnerType());
            hiveCollectorTransformMessageDTO.put("temporary", table.isTemporary());
            return hiveCollectorTransformMessageDTO;
        }).collect(Collectors.toList());
    }

}
