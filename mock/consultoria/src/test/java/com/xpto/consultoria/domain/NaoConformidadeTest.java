package com.xpto.consultoria.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.xpto.consultoria.web.rest.TestUtil;

public class NaoConformidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NaoConformidade.class);
        NaoConformidade naoConformidade1 = new NaoConformidade();
        naoConformidade1.setId(1L);
        NaoConformidade naoConformidade2 = new NaoConformidade();
        naoConformidade2.setId(naoConformidade1.getId());
        assertThat(naoConformidade1).isEqualTo(naoConformidade2);
        naoConformidade2.setId(2L);
        assertThat(naoConformidade1).isNotEqualTo(naoConformidade2);
        naoConformidade1.setId(null);
        assertThat(naoConformidade1).isNotEqualTo(naoConformidade2);
    }
}
