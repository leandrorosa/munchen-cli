package com.lrosa.munchencli;

import com.lrosa.munchencli.domain.MunchenService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication
public class MunchenCliApplication {

	public static void main(String[] args) {
		SpringApplication.run(MunchenCliApplication.class, args);
		new CommandLine(new MunchenService()).execute(args);
		System.exit(0);
	}
}
