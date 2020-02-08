package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class CategoriaNormaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaNorma.class);
        CategoriaNorma categoriaNorma1 = new CategoriaNorma();
        categoriaNorma1.setId(1L);
        CategoriaNorma categoriaNorma2 = new CategoriaNorma();
        categoriaNorma2.setId(categoriaNorma1.getId());
        assertThat(categoriaNorma1).isEqualTo(categoriaNorma2);
        categoriaNorma2.setId(2L);
        assertThat(categoriaNorma1).isNotEqualTo(categoriaNorma2);
        categoriaNorma1.setId(null);
        assertThat(categoriaNorma1).isNotEqualTo(categoriaNorma2);
    }
}
