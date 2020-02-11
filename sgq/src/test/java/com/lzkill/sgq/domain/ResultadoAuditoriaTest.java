package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class ResultadoAuditoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultadoAuditoria.class);
        ResultadoAuditoria resultadoAuditoria1 = new ResultadoAuditoria();
        resultadoAuditoria1.setId(1L);
        ResultadoAuditoria resultadoAuditoria2 = new ResultadoAuditoria();
        resultadoAuditoria2.setId(resultadoAuditoria1.getId());
        assertThat(resultadoAuditoria1).isEqualTo(resultadoAuditoria2);
        resultadoAuditoria2.setId(2L);
        assertThat(resultadoAuditoria1).isNotEqualTo(resultadoAuditoria2);
        resultadoAuditoria1.setId(null);
        assertThat(resultadoAuditoria1).isNotEqualTo(resultadoAuditoria2);
    }
}
