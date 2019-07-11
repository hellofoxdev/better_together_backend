package com.sebastianfox.food.repository.Invitation;

import com.sebastianfox.food.models.Invitation;
import com.sebastianfox.food.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@SuppressWarnings("unused")
public interface InvitationRepository extends CrudRepository<Invitation, Long> {

    Invitation findById(Integer id);
    Invitation findByInvitedBy(User user);
    Invitation findByInvited(User user);
    List<Invitation> findByEmail(String email);
}