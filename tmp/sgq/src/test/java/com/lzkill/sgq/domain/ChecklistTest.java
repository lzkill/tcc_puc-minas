package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class ChecklistTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Checklist.class);
        Checklist checklist1 = new Checklist();
        checklist1.setId(1L);
        Checklist checklist2 = new Checklist();
        checklist2.setId(checklist1.getId());
        assertThat(checklist1).isEqualTo(checklist2);
        checklist2.setId(2L);
        assertThat(checklist1).isNotEqualTo(checklist2);
        checklist1.setId(null);
        assertThat(checklist1).isNotEqualTo(checklist2);
    }
}
