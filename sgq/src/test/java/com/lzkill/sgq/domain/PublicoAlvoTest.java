package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class PublicoAlvoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicoAlvo.class);
        PublicoAlvo publicoAlvo1 = new PublicoAlvo();
        publicoAlvo1.setId(1L);
        PublicoAlvo publicoAlvo2 = new PublicoAlvo();
        publicoAlvo2.setId(publicoAlvo1.getId());
        assertThat(publicoAlvo1).isEqualTo(publicoAlvo2);
        publicoAlvo2.setId(2L);
        assertThat(publicoAlvo1).isNotEqualTo(publicoAlvo2);
        publicoAlvo1.setId(null);
        assertThat(publicoAlvo1).isNotEqualTo(publicoAlvo2);
    }
}
