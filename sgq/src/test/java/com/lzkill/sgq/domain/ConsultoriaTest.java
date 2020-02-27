package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class ConsultoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consultoria.class);
        Consultoria consultoria1 = new Consultoria();
        consultoria1.setId(1L);
        Consultoria consultoria2 = new Consultoria();
        consultoria2.setId(consultoria1.getId());
        assertThat(consultoria1).isEqualTo(consultoria2);
        consultoria2.setId(2L);
        assertThat(consultoria1).isNotEqualTo(consultoria2);
        consultoria1.setId(null);
        assertThat(consultoria1).isNotEqualTo(consultoria2);
    }
}
