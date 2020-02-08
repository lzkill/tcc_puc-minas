package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class ResultadoItemChecklistTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultadoItemChecklist.class);
        ResultadoItemChecklist resultadoItemChecklist1 = new ResultadoItemChecklist();
        resultadoItemChecklist1.setId(1L);
        ResultadoItemChecklist resultadoItemChecklist2 = new ResultadoItemChecklist();
        resultadoItemChecklist2.setId(resultadoItemChecklist1.getId());
        assertThat(resultadoItemChecklist1).isEqualTo(resultadoItemChecklist2);
        resultadoItemChecklist2.setId(2L);
        assertThat(resultadoItemChecklist1).isNotEqualTo(resultadoItemChecklist2);
        resultadoItemChecklist1.setId(null);
        assertThat(resultadoItemChecklist1).isNotEqualTo(resultadoItemChecklist2);
    }
}
