package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class SolicitacaoAnaliseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitacaoAnalise.class);
        SolicitacaoAnalise solicitacaoAnalise1 = new SolicitacaoAnalise();
        solicitacaoAnalise1.setId(1L);
        SolicitacaoAnalise solicitacaoAnalise2 = new SolicitacaoAnalise();
        solicitacaoAnalise2.setId(solicitacaoAnalise1.getId());
        assertThat(solicitacaoAnalise1).isEqualTo(solicitacaoAnalise2);
        solicitacaoAnalise2.setId(2L);
        assertThat(solicitacaoAnalise1).isNotEqualTo(solicitacaoAnalise2);
        solicitacaoAnalise1.setId(null);
        assertThat(solicitacaoAnalise1).isNotEqualTo(solicitacaoAnalise2);
    }
}
