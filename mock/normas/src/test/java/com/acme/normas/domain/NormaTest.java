package com.acme.normas.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.acme.normas.web.rest.TestUtil;

public class NormaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Norma.class);
        Norma norma1 = new Norma();
        norma1.setId(1L);
        Norma norma2 = new Norma();
        norma2.setId(norma1.getId());
        assertThat(norma1).isEqualTo(norma2);
        norma2.setId(2L);
        assertThat(norma1).isNotEqualTo(norma2);
        norma1.setId(null);
        assertThat(norma1).isNotEqualTo(norma2);
    }
}
