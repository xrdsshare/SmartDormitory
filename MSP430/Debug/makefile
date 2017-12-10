################################################################################
# Automatically-generated file. Do not edit!
################################################################################

SHELL = cmd.exe

CG_TOOL_ROOT := E:/Installwin10/TI/ccsv5/tools/compiler/msp430_4.2.1

ORDERED_OBJS += \
$(GEN_CMDS__FLAG) \
"./51/C51.obj" \
"./Alarm/Alarm.obj" \
"./DHT11/DHT11.obj" \
"./DS1302/DS1302.obj" \
"./HC05/HC05.obj" \
"./LCD12864/Lcd12864.obj" \
"./LCD12864/Display.obj" \
"./LED/LED.obj" \
"./Model/Model.obj" \
"./Schedule/Schedule.obj" \
"./main.obj" \
"../lnk_msp430f5529.cmd" \
-l"libmath.a" \
-l"libc.a" \

-include ../makefile.init

RM := DEL /F
RMDIR := RMDIR /S/Q

# All of the sources participating in the build are defined here
-include sources.mk
-include subdir_vars.mk
-include Schedule/subdir_vars.mk
-include Model/subdir_vars.mk
-include LED/subdir_vars.mk
-include LCD12864/subdir_vars.mk
-include HC05/subdir_vars.mk
-include DS1302/subdir_vars.mk
-include DHT11/subdir_vars.mk
-include Alarm/subdir_vars.mk
-include 51/subdir_vars.mk
-include subdir_rules.mk
-include Schedule/subdir_rules.mk
-include Model/subdir_rules.mk
-include LED/subdir_rules.mk
-include LCD12864/subdir_rules.mk
-include HC05/subdir_rules.mk
-include DS1302/subdir_rules.mk
-include DHT11/subdir_rules.mk
-include Alarm/subdir_rules.mk
-include 51/subdir_rules.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(S_DEPS)),)
-include $(S_DEPS)
endif
ifneq ($(strip $(S_UPPER_DEPS)),)
-include $(S_UPPER_DEPS)
endif
ifneq ($(strip $(S62_DEPS)),)
-include $(S62_DEPS)
endif
ifneq ($(strip $(C64_DEPS)),)
-include $(C64_DEPS)
endif
ifneq ($(strip $(ASM_DEPS)),)
-include $(ASM_DEPS)
endif
ifneq ($(strip $(CC_DEPS)),)
-include $(CC_DEPS)
endif
ifneq ($(strip $(S55_DEPS)),)
-include $(S55_DEPS)
endif
ifneq ($(strip $(C67_DEPS)),)
-include $(C67_DEPS)
endif
ifneq ($(strip $(C??_DEPS)),)
-include $(C??_DEPS)
endif
ifneq ($(strip $(CLA_DEPS)),)
-include $(CLA_DEPS)
endif
ifneq ($(strip $(CPP_DEPS)),)
-include $(CPP_DEPS)
endif
ifneq ($(strip $(S??_DEPS)),)
-include $(S??_DEPS)
endif
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
ifneq ($(strip $(C62_DEPS)),)
-include $(C62_DEPS)
endif
ifneq ($(strip $(CXX_DEPS)),)
-include $(CXX_DEPS)
endif
ifneq ($(strip $(C++_DEPS)),)
-include $(C++_DEPS)
endif
ifneq ($(strip $(ASM_UPPER_DEPS)),)
-include $(ASM_UPPER_DEPS)
endif
ifneq ($(strip $(K_DEPS)),)
-include $(K_DEPS)
endif
ifneq ($(strip $(C43_DEPS)),)
-include $(C43_DEPS)
endif
ifneq ($(strip $(S67_DEPS)),)
-include $(S67_DEPS)
endif
ifneq ($(strip $(SA_DEPS)),)
-include $(SA_DEPS)
endif
ifneq ($(strip $(S43_DEPS)),)
-include $(S43_DEPS)
endif
ifneq ($(strip $(OPT_DEPS)),)
-include $(OPT_DEPS)
endif
ifneq ($(strip $(S64_DEPS)),)
-include $(S64_DEPS)
endif
ifneq ($(strip $(C_UPPER_DEPS)),)
-include $(C_UPPER_DEPS)
endif
ifneq ($(strip $(C55_DEPS)),)
-include $(C55_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 

# All Target
all: X002SmartDormitory-Model.out

# Tool invocations
X002SmartDormitory-Model.out: $(OBJS) $(CMD_SRCS) $(GEN_CMDS)
	@echo 'Building target: $@'
	@echo 'Invoking: MSP430 Linker'
	"E:/Installwin10/TI/ccsv5/tools/compiler/msp430_4.2.1/bin/cl430" -vmspx --abi=eabi --advice:power=all -g --define=__MSP430F5529__ --diag_warning=225 --display_error_number --diag_wrap=off --silicon_errata=CPU21 --silicon_errata=CPU22 --silicon_errata=CPU23 --silicon_errata=CPU40 --printf_support=minimal -z -m"X002SmartDormitory-Model.map" --heap_size=160 --stack_size=160 --use_hw_mpy=F5 -i"E:/Installwin10/TI/ccsv5/ccs_base/msp430/include" -i"E:/Installwin10/TI/ccsv5/tools/compiler/msp430_4.2.1/lib" -i"E:/Installwin10/TI/ccsv5/tools/compiler/msp430_4.2.1/include" -i"E:/Installwin10/TI/ccsv5/ccs_base/msp430/lib" --reread_libs --warn_sections --display_error_number --diag_wrap=off --xml_link_info="X002SmartDormitory-Model_linkInfo.xml" --rom_model -o "X002SmartDormitory-Model.out" $(ORDERED_OBJS)
	@echo 'Finished building target: $@'
	@echo ' '

# Other Targets
clean:
	-$(RM) $(MSP430_EXECUTABLE_OUTPUTS__QUOTED) "X002SmartDormitory-Model.out"
	-$(RM) "main.pp" "Schedule\Schedule.pp" "Model\Model.pp" "LED\LED.pp" "LCD12864\Display.pp" "LCD12864\Lcd12864.pp" "HC05\HC05.pp" "DS1302\DS1302.pp" "DHT11\DHT11.pp" "Alarm\Alarm.pp" "51\C51.pp" 
	-$(RM) "main.obj" "Schedule\Schedule.obj" "Model\Model.obj" "LED\LED.obj" "LCD12864\Display.obj" "LCD12864\Lcd12864.obj" "HC05\HC05.obj" "DS1302\DS1302.obj" "DHT11\DHT11.obj" "Alarm\Alarm.obj" "51\C51.obj" 
	-@echo 'Finished clean'
	-@echo ' '

.PHONY: all clean dependents
.SECONDARY:

-include ../makefile.targets

