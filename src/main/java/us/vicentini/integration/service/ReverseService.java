package us.vicentini.integration.service;

public class ReverseService {

	public void reverse(String message){
		System.out.println(new StringBuilder(message).reverse().toString());
	}
}
