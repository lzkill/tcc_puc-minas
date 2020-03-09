package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class AuditoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auditoria.class);
        Auditoria auditoria1 = new Auditoria();
        auditoria1.setId(1L);
        Auditoria auditoria2 = new Auditoria();
        auditoria2.setId(auditoria1.getId());
        assertThat(auditoria1).isEqualTo(auditoria2);
        auditoria2.setId(2L);
        assertThat(auditoria1).isNotEqualTo(auditoria2);
        auditoria1.setId(null);
        assertThat(auditoria1).isNotEqualTo(auditoria2);
    }
}
