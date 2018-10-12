package com.vault.jtv.services;

import com.vault.util.MessageConstants;

public class UserNotFoundException extends RuntimeException  {
		private static final long serialVersionUID = 1L;

		public UserNotFoundException(Integer id) {
			super(String.format(MessageConstants.NO_USER, id));
			// TODO Auto-generated constructor stub
		}

}
