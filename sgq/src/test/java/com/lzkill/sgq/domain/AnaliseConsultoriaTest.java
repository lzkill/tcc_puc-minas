package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class AnaliseConsultoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnaliseConsultoria.class);
        AnaliseConsultoria analiseConsultoria1 = new AnaliseConsultoria();
        analiseConsultoria1.setId(1L);
        AnaliseConsultoria analiseConsultoria2 = new AnaliseConsultoria();
        analiseConsultoria2.setId(analiseConsultoria1.getId());
        assertThat(analiseConsultoria1).isEqualTo(analiseConsultoria2);
        analiseConsultoria2.setId(2L);
        assertThat(analiseConsultoria1).isNotEqualTo(analiseConsultoria2);
        analiseConsultoria1.setId(null);
        assertThat(analiseConsultoria1).isNotEqualTo(analiseConsultoria2);
    }
}
