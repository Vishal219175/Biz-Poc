package com.example.mvc_mongodb.solr;

import com.example.mvc_mongodb.model.Product;
import com.example.mvc_mongodb.repository.ProductRepository;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Date;
import java.util.List;

@Component
public class MongoToSolrSync {
    private static final Logger logger = LogManager.getLogger(MongoToSolrSync.class);
    private final ProductRepository productRepository;
    private final SolrClient solrClient;

    @Autowired
    public MongoToSolrSync(ProductRepository productRepository,
                           @Value("${spring.data.solr.host}/products") String solrUrl) {
        this.productRepository = productRepository;
        this.solrClient = new HttpSolrClient.Builder(solrUrl).build();
    }

    @Scheduled(fixedDelay = 600000) // Run every 10 minutes (600000ms)
    public void syncUnsyncedProducts() {
        logger.info("Scheduled Sync: Checking for unsynced or updated products...");
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            try {
                if (shouldSync(product)) {
                    syncToSolr(product);
                } else {
                    logger.info("Skipping already synced product: " + product.getId());
                }
            } catch (Exception e) {
                logger.error("Error checking product in Solr", e);
            }
        }
    }

    private boolean shouldSync(Product product) {
        try {
            SolrDocument solrProduct = solrClient.getById(product.getId());
            if (solrProduct == null) {
                return true; // Product is missing in Solr, sync it
            }

            Date solrUpdatedDate = (Date) solrProduct.getFieldValue("updatedOn");

            if (solrUpdatedDate == null) {
                return true; // No update timestamp in Solr, sync it
            }

            return product.getUpdatedOn().after(solrUpdatedDate);
        } catch (Exception e) {
            logger.error("Error checking product in Solr", e);
            return true; // Default to syncing if there's an error
        }
    }

    public void syncToSolr(Product product) {
        try {
            SolrInputDocument solrDoc = new SolrInputDocument();
            solrDoc.addField("id", product.getId());
            solrDoc.addField("name", product.getName());
            solrDoc.addField("description", product.getDescription());
            solrDoc.addField("price", product.getPrice());
            solrDoc.addField("createdOn", product.getCreatedOn());
            solrDoc.addField("updatedOn", product.getUpdatedOn());

            solrClient.add(solrDoc);
            solrClient.commit();
            logger.info("Synced Product to Solr: " + product.getId());
        } catch (Exception e) {
            logger.error("Error syncing product to Solr", e);
        }
    }
}
