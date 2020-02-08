package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class SetorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Setor.class);
        Setor setor1 = new Setor();
        setor1.setId(1L);
        Setor setor2 = new Setor();
        setor2.setId(setor1.getId());
        assertThat(setor1).isEqualTo(setor2);
        setor2.setId(2L);
        assertThat(setor1).isNotEqualTo(setor2);
        setor1.setId(null);
        assertThat(setor1).isNotEqualTo(setor2);
    }
}
