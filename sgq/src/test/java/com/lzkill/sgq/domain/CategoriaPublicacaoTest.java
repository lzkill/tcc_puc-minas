package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class CategoriaPublicacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaPublicacao.class);
        CategoriaPublicacao categoriaPublicacao1 = new CategoriaPublicacao();
        categoriaPublicacao1.setId(1L);
        CategoriaPublicacao categoriaPublicacao2 = new CategoriaPublicacao();
        categoriaPublicacao2.setId(categoriaPublicacao1.getId());
        assertThat(categoriaPublicacao1).isEqualTo(categoriaPublicacao2);
        categoriaPublicacao2.setId(2L);
        assertThat(categoriaPublicacao1).isNotEqualTo(categoriaPublicacao2);
        categoriaPublicacao1.setId(null);
        assertThat(categoriaPublicacao1).isNotEqualTo(categoriaPublicacao2);
    }
}
