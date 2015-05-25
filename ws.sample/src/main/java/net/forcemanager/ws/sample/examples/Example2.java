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

package net.forcemanager.ws.sample.examples;

import net.forcemanager.ws.sample.factories.RequestFactory;
import net.forcemanager.ws.sample.models.Company;
import net.forcemanager.ws.sample.utility.FMEntity;

/* In this example:
 * First: create a company and save it in ForceManager
 * Then: search the company created
 * Finally: delete the company created
 */

public class Example2 {

	public static void main(String[] args) {
		Company[] companies = null;
		String query;

		// create a new Company
		Company company = getNewSampleCompany();

		// INSERT it in Force manager
		boolean inserted = RequestFactory.InsertEntity(FMEntity.COMPANIES,
				Company.class, company);

		if (inserted) {
			// find it in Force manager
			query = "account_type_id='" + company.getAccount_type_id()
					+ "' AND name='" + company.getName()
					+ "' AND deleted='false'";

			companies = RequestFactory.CreateReadRequest(FMEntity.COMPANIES,
					Company[].class, query);

			if (companies == null) {
				// companies not found
				return;
			}

			company = companies[0];
			System.out.println("Entity inserted: " + company.toString());
		}

		// UPDATE company name
		updateSampleCompany(company);

		boolean updated = RequestFactory.UpdateEntity(FMEntity.COMPANIES,
				company);

		if (updated) {
			// find it in Force manager and check if updated
			query = "id='" + company.getId() + "' AND deleted='false'";

			companies = RequestFactory.CreateReadRequest(FMEntity.COMPANIES,
					Company[].class, query);

			if (companies != null && companies.length > 0) {
				company = companies[0];
				System.out.println("Entity updated: " + company.toString());
			}
		}

		// DELETE company
		boolean deleted = RequestFactory.DeleteEntity(FMEntity.COMPANIES,
				company);

		if (deleted) {
			// find it in Force manager and check that company was deleted
			query = "id='" + company.getId() + "'";

			companies = RequestFactory.CreateReadRequest(FMEntity.COMPANIES,
					Company[].class, query);

			if (companies == null
					|| (companies != null && companies.length == 0)) {
				return;
			}

			company = companies[0];
			System.out.println("Entity deleted? " + company.getDeleted());
		}
	}

	private static Company getNewSampleCompany() {
		Company company = new Company();

		// Mandatory fields
		company.setName("Test RESTful API");
		company.setAccount_type_id("23");

		// Optional fields
		company.setPhone("817271761");
		company.setBranch_id("17");
		company.setExt_id("112341");

		return company;
	}

	private static void updateSampleCompany(Company company) {
		company.setCity_name("Barcelona");
		company.setMobile_phone("0034567123456");
		company.setPhone("");
	}
}