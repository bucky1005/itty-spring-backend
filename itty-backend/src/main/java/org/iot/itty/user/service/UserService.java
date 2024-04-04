package org.iot.itty.user.service;

import org.iot.itty.dto.UserDTO;

public interface UserService {
	UserDTO modifyUser(UserDTO userDTO);

	UserDTO selectUserByUserCodePk(int userCodePk);
}
