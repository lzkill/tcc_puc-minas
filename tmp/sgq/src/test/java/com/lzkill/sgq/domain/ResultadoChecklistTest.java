package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class ResultadoChecklistTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultadoChecklist.class);
        ResultadoChecklist resultadoChecklist1 = new ResultadoChecklist();
        resultadoChecklist1.setId(1L);
        ResultadoChecklist resultadoChecklist2 = new ResultadoChecklist();
        resultadoChecklist2.setId(resultadoChecklist1.getId());
        assertThat(resultadoChecklist1).isEqualTo(resultadoChecklist2);
        resultadoChecklist2.setId(2L);
        assertThat(resultadoChecklist1).isNotEqualTo(resultadoChecklist2);
        resultadoChecklist1.setId(null);
        assertThat(resultadoChecklist1).isNotEqualTo(resultadoChecklist2);
    }
}
