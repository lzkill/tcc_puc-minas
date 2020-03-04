package com.xpto.consultoria.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.xpto.consultoria.web.rest.TestUtil;

public class AnexoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anexo.class);
        Anexo anexo1 = new Anexo();
        anexo1.setId(1L);
        Anexo anexo2 = new Anexo();
        anexo2.setId(anexo1.getId());
        assertThat(anexo1).isEqualTo(anexo2);
        anexo2.setId(2L);
        assertThat(anexo1).isNotEqualTo(anexo2);
        anexo1.setId(null);
        assertThat(anexo1).isNotEqualTo(anexo2);
    }
}
