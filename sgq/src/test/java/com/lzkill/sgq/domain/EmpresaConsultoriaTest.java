package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class EmpresaConsultoriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpresaConsultoria.class);
        EmpresaConsultoria empresaConsultoria1 = new EmpresaConsultoria();
        empresaConsultoria1.setId(1L);
        EmpresaConsultoria empresaConsultoria2 = new EmpresaConsultoria();
        empresaConsultoria2.setId(empresaConsultoria1.getId());
        assertThat(empresaConsultoria1).isEqualTo(empresaConsultoria2);
        empresaConsultoria2.setId(2L);
        assertThat(empresaConsultoria1).isNotEqualTo(empresaConsultoria2);
        empresaConsultoria1.setId(null);
        assertThat(empresaConsultoria1).isNotEqualTo(empresaConsultoria2);
    }
}
