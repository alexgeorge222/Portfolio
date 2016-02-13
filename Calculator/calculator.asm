.text
clear:
	add	$t0, $zero, $zero	#operator 1
	add	$t1, $zero, $zero	#operator 2
	add	$t2, $zero, $zero	#base user input
	add	$t3, $zero, $zero	#switcher
	add	$t4, $zero, $zero	#divisor switcher
	add	$t5, $zero, $zero	#first number flipper
	add	$t6, $zero, $zero	#second number flipper
	add	$s0, $zero, $zero	#operand 1
	add	$s1, $zero, $zero	#multiply loop
	add	$s2, $zero, $zero	#dispayed value for $t8
	addi	$s3, $zero, 10		#operator check
	add	$s4, $zero, $zero	#expander counter number counter
	add	$s5, $zero, $zero	#expander helper
	add	$t9, $zero, $zero
	add	$t8, $zero, $zero

base:
	beq	$t9, $zero, base	#stays until number called
	sll	$t9, $t9, 1
	srl	$t9, $t9, 1		#adjusts incoming input
	add	$t2, $zero, $t9
	slti	$t3, $t2, 10		#checks to see if input is a number or not
	beq	$t3, 0, operand
	beq	$t5, 0, firstNumber
	beq	$t6, 0, secondNumber
	
firstNumber:
	bne	$t0, 0, firstExpand	#if a number has already been entered, this will allow expansion
	add	$t0, $zero, $t9
	add	$t9, $zero, $zero
	add	$t8, $zero, $t0
	j	base
	
firstExpand:
	add	$s5, $zero, $t0
	add	$s4, $s4, 1
firstLoop:
	add	$t0, $t0, $s5		#multiplies by 10 than adds new number
	add	$s4, $s4, 1
	bne	$s4, $s3, firstLoop
	add	$s4, $zero, $zero
	add	$s5, $zero, $zero
	add	$t0, $t0, $t9
	add	$t9, $zero, $zero
	add	$t8, $zero, $t0
	j	base
	
secondNumber:
	bne	$t1, 0, secondExpand	#works the same as first number
	add	$t1, $zero, $t9
	add	$t9, $zero, $zero
	add	$t8, $zero, $t1
	j	base
	
secondExpand:
	add	$s5, $zero, $t1
	add	$s4, $s4, 1
secondLoop:
	add	$t1, $t1, $s5
	add	$s4, $s4, 1
	bne	$s4, $s3, secondLoop
	add	$s4, $zero, $zero
	add	$s5, $zero, $zero
	add	$t1, $t1, $t9
	add	$t9, $zero, $zero
	add	$t8, $zero, $t1
	j	base
	

operand:
	add	$t9, $zero, $zero	#if operandsd are used, theyy go here first
	beq	$t2, 14, equals
	beq	$t2, 15, clear
	addi	$t5, $zero, 1
	add	$s0, $zero, $t2
	j	base
adder:
	add	$s2, $t0, $t1		#adds numbers
	add	$t8, $zero, $s2
	j	houseCleaning
	
subtract:
	sub	$s2, $t0, $t1		#subtracts numbers
	add	$t8, $zero, $s2
	j	houseCleaning
		

multiply:
	beq	$s1, $t1, zeroMulti	#uses a loop to multiply
	add	$s2, $s2, $t0
	addi	$s1, $s1, 1
	bne	$s1, $t1, multiply
	add	$s1, $zero, $zero
	add	$t8, $zero, $s2
	j	houseCleaning
	
	
zeroMulti:
	add	$s2, $zero, $t1		#if multiplying be zero
	add	$t8, $zero, $s2
	j	houseCleaning
	
	
divisor:
	beq	$s1, $t0, zeroDivide	#uses loop to divide
divideLoop:
	sub	$t0, $t0, $t1
	addi	$s2, $s2, 1
	slti	$t4, $t0, 1
	beq	$t4, 1, divideSuccess
	j	divideLoop
divideSuccess:				#if the divide is a sucess
	add	$t8, $zero, $s2	
	j	houseCleaning
	
	
zeroDivide:				#if trying to divide anything by zero
	add	$s2, $zero, $t0
	add	$t8, $zero, $s2
	j	houseCleaning
	
equals:					#branch for operands once equals is hit
	add	$t9, $zero, $zero
	addi	$t6, $zero, 1
	beq	$s0, 10, adder
	beq	$s0, 11, subtract
	beq	$s0, 12, multiply
	beq	$s0, 13, divisor
	
houseCleaning:				#cleans out varoius nooks and crannies of the code
	add	$t9, $zero, $zero
	add	$s1, $zero, $zero
	add	$t6, $zero, $zero
	add	$t0, $zero, $s2
	add	$s2, $zero, $zero
	add	$t1, $zero, $zero
	j	base
	
	
	
	

			
