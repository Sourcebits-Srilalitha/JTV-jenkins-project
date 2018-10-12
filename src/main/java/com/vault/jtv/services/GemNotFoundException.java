package com.vault.jtv.services;

import com.vault.util.MessageConstants;

public class GemNotFoundException extends RuntimeException  {
		private static final long serialVersionUID = 1L;

		public GemNotFoundException(Integer id) {
			super(String.format(MessageConstants.NO_GEM, id));
			// TODO Auto-generated constructor stub
		}

}
