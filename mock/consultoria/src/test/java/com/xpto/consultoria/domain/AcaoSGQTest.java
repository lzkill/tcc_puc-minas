package com.xpto.consultoria.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.xpto.consultoria.web.rest.TestUtil;

public class AcaoSGQTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcaoSGQ.class);
        AcaoSGQ acaoSGQ1 = new AcaoSGQ();
        acaoSGQ1.setId(1L);
        AcaoSGQ acaoSGQ2 = new AcaoSGQ();
        acaoSGQ2.setId(acaoSGQ1.getId());
        assertThat(acaoSGQ1).isEqualTo(acaoSGQ2);
        acaoSGQ2.setId(2L);
        assertThat(acaoSGQ1).isNotEqualTo(acaoSGQ2);
        acaoSGQ1.setId(null);
        assertThat(acaoSGQ1).isNotEqualTo(acaoSGQ2);
    }
}
