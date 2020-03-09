package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class PlanoAuditoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoAuditoria.class);
        PlanoAuditoria planoAuditoria1 = new PlanoAuditoria();
        planoAuditoria1.setId(1L);
        PlanoAuditoria planoAuditoria2 = new PlanoAuditoria();
        planoAuditoria2.setId(planoAuditoria1.getId());
        assertThat(planoAuditoria1).isEqualTo(planoAuditoria2);
        planoAuditoria2.setId(2L);
        assertThat(planoAuditoria1).isNotEqualTo(planoAuditoria2);
        planoAuditoria1.setId(null);
        assertThat(planoAuditoria1).isNotEqualTo(planoAuditoria2);
    }
}
