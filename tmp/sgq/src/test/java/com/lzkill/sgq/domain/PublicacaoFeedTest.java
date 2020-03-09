package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class PublicacaoFeedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicacaoFeed.class);
        PublicacaoFeed publicacaoFeed1 = new PublicacaoFeed();
        publicacaoFeed1.setId(1L);
        PublicacaoFeed publicacaoFeed2 = new PublicacaoFeed();
        publicacaoFeed2.setId(publicacaoFeed1.getId());
        assertThat(publicacaoFeed1).isEqualTo(publicacaoFeed2);
        publicacaoFeed2.setId(2L);
        assertThat(publicacaoFeed1).isNotEqualTo(publicacaoFeed2);
        publicacaoFeed1.setId(null);
        assertThat(publicacaoFeed1).isNotEqualTo(publicacaoFeed2);
    }
}
