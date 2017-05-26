package miasi_bank;

public aspect Prowizja {
	pointcut callSayHello(): call(* miasi_bank.BankSystem.elo(..));
	
	after() : callSayHello() {
		System.out.println("Hej!");
	}
}
