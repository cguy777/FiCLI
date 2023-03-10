/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2023, Noah McLean
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fiberous.example;

import fiberous.fi.FiCLI;
import fiberous.fi.FiState;

/*
 * Quick example of how this works...
 */
public class ExampleCLI {
	
	public static void main(String[]args) {
		//Creating a parser using the defaults of System.in and System.out.
		//'?' is the default command listing string.
		//When typed, it will list all of the available commands.
		FiCLI parser = new FiCLI("?");
		
		//The commands will be added and sorted in alphabetical order
		parser.addCommand(new NestedCommands("menu"));
		parser.addCommand(new ArgTestCommand("args"));
		parser.addCommand(new DisplayCommand("get record"));
		parser.addCommand(new HiddenCommand("hidden command"));
		
		//This command is not needed as this is the default caret.
		parser.setCaret("> ");
		
		while(true) {
			//Creating an FiState object which can allow us to understand what the system saw.
			//It also allows us to see what the input was.
			//the .processCommand() prints the caret and then waits for input.
			FiState state = parser.processCommand();
			
			//Check for the return states
			if(state.state == FiState.EXIT)
				System.exit(0);
			
			if(state.state == FiState.INVALID)
				System.out.println('"' + state.input + '"' + " is an invalid command");
			
		}
	}
}
