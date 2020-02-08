package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class CampanhaRecallTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampanhaRecall.class);
        CampanhaRecall campanhaRecall1 = new CampanhaRecall();
        campanhaRecall1.setId(1L);
        CampanhaRecall campanhaRecall2 = new CampanhaRecall();
        campanhaRecall2.setId(campanhaRecall1.getId());
        assertThat(campanhaRecall1).isEqualTo(campanhaRecall2);
        campanhaRecall2.setId(2L);
        assertThat(campanhaRecall1).isNotEqualTo(campanhaRecall2);
        campanhaRecall1.setId(null);
        assertThat(campanhaRecall1).isNotEqualTo(campanhaRecall2);
    }
}
