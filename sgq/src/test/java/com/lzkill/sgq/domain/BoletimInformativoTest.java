package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class BoletimInformativoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoletimInformativo.class);
        BoletimInformativo boletimInformativo1 = new BoletimInformativo();
        boletimInformativo1.setId(1L);
        BoletimInformativo boletimInformativo2 = new BoletimInformativo();
        boletimInformativo2.setId(boletimInformativo1.getId());
        assertThat(boletimInformativo1).isEqualTo(boletimInformativo2);
        boletimInformativo2.setId(2L);
        assertThat(boletimInformativo1).isNotEqualTo(boletimInformativo2);
        boletimInformativo1.setId(null);
        assertThat(boletimInformativo1).isNotEqualTo(boletimInformativo2);
    }
}
