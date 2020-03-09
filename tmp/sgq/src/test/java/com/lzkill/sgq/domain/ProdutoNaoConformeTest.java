package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class ProdutoNaoConformeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdutoNaoConforme.class);
        ProdutoNaoConforme produtoNaoConforme1 = new ProdutoNaoConforme();
        produtoNaoConforme1.setId(1L);
        ProdutoNaoConforme produtoNaoConforme2 = new ProdutoNaoConforme();
        produtoNaoConforme2.setId(produtoNaoConforme1.getId());
        assertThat(produtoNaoConforme1).isEqualTo(produtoNaoConforme2);
        produtoNaoConforme2.setId(2L);
        assertThat(produtoNaoConforme1).isNotEqualTo(produtoNaoConforme2);
        produtoNaoConforme1.setId(null);
        assertThat(produtoNaoConforme1).isNotEqualTo(produtoNaoConforme2);
    }
}
