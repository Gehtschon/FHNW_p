package logger;

import rule.I_Rule;
import rule.Ruletype;

public interface I_Logger {

	void freereport(String message);

	void rulebroken(I_Rule rule);

	void rulebroken(I_Rule rule, Ruletype ruletype);
}
