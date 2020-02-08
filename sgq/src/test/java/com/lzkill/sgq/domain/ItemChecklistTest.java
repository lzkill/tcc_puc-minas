package com.lzkill.sgq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lzkill.sgq.web.rest.TestUtil;

public class ItemChecklistTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemChecklist.class);
        ItemChecklist itemChecklist1 = new ItemChecklist();
        itemChecklist1.setId(1L);
        ItemChecklist itemChecklist2 = new ItemChecklist();
        itemChecklist2.setId(itemChecklist1.getId());
        assertThat(itemChecklist1).isEqualTo(itemChecklist2);
        itemChecklist2.setId(2L);
        assertThat(itemChecklist1).isNotEqualTo(itemChecklist2);
        itemChecklist1.setId(null);
        assertThat(itemChecklist1).isNotEqualTo(itemChecklist2);
    }
}
