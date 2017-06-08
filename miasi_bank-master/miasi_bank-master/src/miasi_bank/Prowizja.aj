package miasi_bank;

public aspect Prowizja {
	double prowizja = 2.0;
	
	double around(String clientID, String accountID, double amount) : call(public double miasi_bank.Bank.withdraw(String, String, double)) && args(clientID, accountID, amount){
		System.out.println("Dodajê prowzijê, razem: " + (amount + prowizja));
		
		return proceed(clientID, accountID, amount + prowizja);
	}
}
