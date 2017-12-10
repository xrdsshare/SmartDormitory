################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Each subdirectory must supply rules for building sources it contributes
Alarm/Alarm.obj: ../Alarm/Alarm.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: MSP430 Compiler'
	"E:/Installwin10/TI/ccsv5/tools/compiler/msp430_4.2.1/bin/cl430" -vmspx --abi=eabi --include_path="E:/Installwin10/TI/ccsv5/ccs_base/msp430/include" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/Schedule" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/Alarm" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/DS1302" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/Delay" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/LCD12864" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/DHT11" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/51" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/HC05" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/LED" --include_path="F:/Workspaces/MSP430/X002SmartDormitory-Model/Model" --include_path="E:/Installwin10/TI/ccsv5/tools/compiler/msp430_4.2.1/include" --advice:power=all -g --define=__MSP430F5529__ --diag_warning=225 --display_error_number --diag_wrap=off --silicon_errata=CPU21 --silicon_errata=CPU22 --silicon_errata=CPU23 --silicon_errata=CPU40 --printf_support=minimal --preproc_with_compile --preproc_dependency="Alarm/Alarm.pp" --obj_directory="Alarm" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '


