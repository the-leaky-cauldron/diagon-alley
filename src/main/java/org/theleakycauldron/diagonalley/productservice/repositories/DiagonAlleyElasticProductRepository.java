package org.theleakycauldron.diagonalley.productservice.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.theleakycauldron.diagonalley.productservice.entities.documents.ProductDocument;

import java.util.List;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public interface DiagonAlleyElasticProductRepository extends ElasticsearchRepository<ProductDocument, String> {
    List<ProductDocument> findProductByTagsEquals(List<String> keyword);
}
