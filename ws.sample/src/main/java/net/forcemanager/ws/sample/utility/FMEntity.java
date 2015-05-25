/*******************************************************************************
Copyright (c) 2015, Tritium Software S.L.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Tritium Software S.L., nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*******************************************************************************/

package net.forcemanager.ws.sample.utility;

/* Entity types */

public enum FMEntity {
	// Used to interact with companies
	COMPANIES {
		@Override
		public String toString() {
			return "api/companies";
		}
	},
	// Used to work with contacts
	CONTACTS {
		@Override
		public String toString() {
			return "api/contacts";
		}
	},
	// Used for activities
	ACTIVITIES {
		@Override
		public String toString() {
			return "api/activities";
		}
	},
	// Used for opportunities
	OPPORTUNITIES {
		@Override
		public String toString() {
			return "api/opportunities";
		}
	},
	// Used for products
	PRODUCTS {
		@Override
		public String toString() {
			return "api/products";
		}
	},
	// Used to get list of values for entities
	VALUES {
		@Override
		public String toString() {
			return "api/values";
		}
	},
	// Used to get the FM id of an entity by your system id (external id)
	INTERNALIDS {
		@Override
		public String toString() {
			return "api/internalids";
		}
	}
}