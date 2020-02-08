package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class EventoOperacionalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoOperacional.class);
        EventoOperacional eventoOperacional1 = new EventoOperacional();
        eventoOperacional1.setId(1L);
        EventoOperacional eventoOperacional2 = new EventoOperacional();
        eventoOperacional2.setId(eventoOperacional1.getId());
        assertThat(eventoOperacional1).isEqualTo(eventoOperacional2);
        eventoOperacional2.setId(2L);
        assertThat(eventoOperacional1).isNotEqualTo(eventoOperacional2);
        eventoOperacional1.setId(null);
        assertThat(eventoOperacional1).isNotEqualTo(eventoOperacional2);
    }
}
