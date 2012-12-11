/*
* generated by Xtext
*/
package gov.ca.dwr.wresl.xtext.editor.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import gov.ca.dwr.wresl.xtext.editor.services.WreslEditorGrammarAccess;

public class WreslEditorParser extends AbstractContentAssistParser {
	
	@Inject
	private WreslEditorGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected gov.ca.dwr.wresl.xtext.editor.ui.contentassist.antlr.internal.InternalWreslEditorParser createParser() {
		gov.ca.dwr.wresl.xtext.editor.ui.contentassist.antlr.internal.InternalWreslEditorParser result = new gov.ca.dwr.wresl.xtext.editor.ui.contentassist.antlr.internal.InternalWreslEditorParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getWreslEvaluatorAccess().getAlternatives(), "rule__WreslEvaluator__Alternatives");
					put(grammarAccess.getWreslEvaluatorAccess().getAlternatives_0(), "rule__WreslEvaluator__Alternatives_0");
					put(grammarAccess.getPatternAccess().getAlternatives(), "rule__Pattern__Alternatives");
					put(grammarAccess.getObjectiveAccess().getAlternatives_0(), "rule__Objective__Alternatives_0");
					put(grammarAccess.getObjectiveAccess().getLocalAlternatives_1_1_0(), "rule__Objective__LocalAlternatives_1_1_0");
					put(grammarAccess.getDefineAccess().getAlternatives_0(), "rule__Define__Alternatives_0");
					put(grammarAccess.getDefineAccess().getLocalAlternatives_1_1_0(), "rule__Define__LocalAlternatives_1_1_0");
					put(grammarAccess.getDefineAccess().getDefinitionAlternatives_4_0(), "rule__Define__DefinitionAlternatives_4_0");
					put(grammarAccess.getSvarDefAccess().getAlternatives_0(), "rule__SvarDef__Alternatives_0");
					put(grammarAccess.getSvarDefAccess().getLocalAlternatives_1_1_0(), "rule__SvarDef__LocalAlternatives_1_1_0");
					put(grammarAccess.getDvarDefAccess().getAlternatives_0(), "rule__DvarDef__Alternatives_0");
					put(grammarAccess.getDvarDefAccess().getLocalAlternatives_1_1_0(), "rule__DvarDef__LocalAlternatives_1_1_0");
					put(grammarAccess.getDvarDefAccess().getDefinitionAlternatives_4_0(), "rule__DvarDef__DefinitionAlternatives_4_0");
					put(grammarAccess.getConstDefAccess().getAlternatives_0(), "rule__ConstDef__Alternatives_0");
					put(grammarAccess.getConstDefAccess().getLocalAlternatives_1_1_0(), "rule__ConstDef__LocalAlternatives_1_1_0");
					put(grammarAccess.getExternalAccess().getAlternatives_0(), "rule__External__Alternatives_0");
					put(grammarAccess.getExternalAccess().getAlternatives_1(), "rule__External__Alternatives_1");
					put(grammarAccess.getExternalAccess().getAlternatives_1_0_1(), "rule__External__Alternatives_1_0_1");
					put(grammarAccess.getExternalAccess().getAlternatives_1_1_1(), "rule__External__Alternatives_1_1_1");
					put(grammarAccess.getAliasAccess().getAlternatives_0(), "rule__Alias__Alternatives_0");
					put(grammarAccess.getAliasAccess().getLocalAlternatives_1_1_0(), "rule__Alias__LocalAlternatives_1_1_0");
					put(grammarAccess.getAliasAccess().getAlternatives_4(), "rule__Alias__Alternatives_4");
					put(grammarAccess.getAliasAccess().getAlternatives_6_0(), "rule__Alias__Alternatives_6_0");
					put(grammarAccess.getAliasAccess().getAlternatives_7_0(), "rule__Alias__Alternatives_7_0");
					put(grammarAccess.getDVarAccess().getAlternatives(), "rule__DVar__Alternatives");
					put(grammarAccess.getDVarNonStdAccess().getAlternatives_1(), "rule__DVarNonStd__Alternatives_1");
					put(grammarAccess.getDVarNonStdAccess().getAlternatives_3(), "rule__DVarNonStd__Alternatives_3");
					put(grammarAccess.getDVarStdAccess().getAlternatives_0(), "rule__DVarStd__Alternatives_0");
					put(grammarAccess.getDVarStdAccess().getAlternatives_1(), "rule__DVarStd__Alternatives_1");
					put(grammarAccess.getDVarStdAccess().getAlternatives_3(), "rule__DVarStd__Alternatives_3");
					put(grammarAccess.getDVarIntegerAccess().getAlternatives(), "rule__DVarInteger__Alternatives");
					put(grammarAccess.getDVarIntegerStdAccess().getAlternatives_0(), "rule__DVarIntegerStd__Alternatives_0");
					put(grammarAccess.getDVarIntegerStdAccess().getAlternatives_1(), "rule__DVarIntegerStd__Alternatives_1");
					put(grammarAccess.getDVarIntegerStdAccess().getAlternatives_2(), "rule__DVarIntegerStd__Alternatives_2");
					put(grammarAccess.getDVarIntegerStdAccess().getAlternatives_4(), "rule__DVarIntegerStd__Alternatives_4");
					put(grammarAccess.getDVarIntegerNonStdAccess().getAlternatives_0(), "rule__DVarIntegerNonStd__Alternatives_0");
					put(grammarAccess.getDVarIntegerNonStdAccess().getAlternatives_2(), "rule__DVarIntegerNonStd__Alternatives_2");
					put(grammarAccess.getDVarIntegerNonStdAccess().getAlternatives_4(), "rule__DVarIntegerNonStd__Alternatives_4");
					put(grammarAccess.getSVarAccess().getAlternatives(), "rule__SVar__Alternatives");
					put(grammarAccess.getSVarDSSAccess().getAlternatives_0(), "rule__SVarDSS__Alternatives_0");
					put(grammarAccess.getSVarDSSAccess().getAlternatives_2(), "rule__SVarDSS__Alternatives_2");
					put(grammarAccess.getSVarDSSAccess().getAlternatives_4(), "rule__SVarDSS__Alternatives_4");
					put(grammarAccess.getSVarDSSAccess().getAlternatives_6_0(), "rule__SVarDSS__Alternatives_6_0");
					put(grammarAccess.getSVarExpressionAccess().getAlternatives_0(), "rule__SVarExpression__Alternatives_0");
					put(grammarAccess.getCaseContentAccess().getAlternatives_0(), "rule__CaseContent__Alternatives_0");
					put(grammarAccess.getCaseContentAccess().getAlternatives_4(), "rule__CaseContent__Alternatives_4");
					put(grammarAccess.getSumContentAccess().getAlternatives_0(), "rule__SumContent__Alternatives_0");
					put(grammarAccess.getValueContentAccess().getAlternatives_0(), "rule__ValueContent__Alternatives_0");
					put(grammarAccess.getTableContentAccess().getAlternatives_0(), "rule__TableContent__Alternatives_0");
					put(grammarAccess.getTableContentAccess().getAlternatives_2(), "rule__TableContent__Alternatives_2");
					put(grammarAccess.getTableContentAccess().getAlternatives_4_0(), "rule__TableContent__Alternatives_4_0");
					put(grammarAccess.getTableContentAccess().getAlternatives_4_2(), "rule__TableContent__Alternatives_4_2");
					put(grammarAccess.getTableContentAccess().getAlternatives_5_0(), "rule__TableContent__Alternatives_5_0");
					put(grammarAccess.getTermSimpleAccess().getAlternatives(), "rule__TermSimple__Alternatives");
					put(grammarAccess.getLowerAndOrUpperAccess().getAlternatives(), "rule__LowerAndOrUpper__Alternatives");
					put(grammarAccess.getUpperAccess().getAlternatives_0(), "rule__Upper__Alternatives_0");
					put(grammarAccess.getUpperAccess().getAlternatives_1(), "rule__Upper__Alternatives_1");
					put(grammarAccess.getUpperAccess().getAlternatives_1_0_1(), "rule__Upper__Alternatives_1_0_1");
					put(grammarAccess.getLowerAccess().getAlternatives_0(), "rule__Lower__Alternatives_0");
					put(grammarAccess.getLowerAccess().getAlternatives_1(), "rule__Lower__Alternatives_1");
					put(grammarAccess.getLowerAccess().getAlternatives_1_0_1(), "rule__Lower__Alternatives_1_0_1");
					put(grammarAccess.getGoalAccess().getAlternatives_0(), "rule__Goal__Alternatives_0");
					put(grammarAccess.getGoalAccess().getLocalAlternatives_1_1_0(), "rule__Goal__LocalAlternatives_1_1_0");
					put(grammarAccess.getGoalAccess().getDefinitionAlternatives_4_0(), "rule__Goal__DefinitionAlternatives_4_0");
					put(grammarAccess.getGoalCaseAccess().getAlternatives_0(), "rule__GoalCase__Alternatives_0");
					put(grammarAccess.getGoalCaseAccess().getAlternatives_2(), "rule__GoalCase__Alternatives_2");
					put(grammarAccess.getGoalCaseContentAccess().getAlternatives_0(), "rule__GoalCaseContent__Alternatives_0");
					put(grammarAccess.getGoalCaseContentAccess().getAlternatives_4(), "rule__GoalCaseContent__Alternatives_4");
					put(grammarAccess.getGoalNoCaseContentAccess().getAlternatives_0(), "rule__GoalNoCaseContent__Alternatives_0");
					put(grammarAccess.getSubContentAccess().getAlternatives(), "rule__SubContent__Alternatives");
					put(grammarAccess.getLhsGtRhsAccess().getAlternatives_0(), "rule__LhsGtRhs__Alternatives_0");
					put(grammarAccess.getLhsGtRhsAccess().getAlternatives_2(), "rule__LhsGtRhs__Alternatives_2");
					put(grammarAccess.getLhsGtRhsAccess().getAlternatives_3(), "rule__LhsGtRhs__Alternatives_3");
					put(grammarAccess.getLhsGtRhsAccess().getAlternatives_3_0_1(), "rule__LhsGtRhs__Alternatives_3_0_1");
					put(grammarAccess.getLhsLtRhsAccess().getAlternatives_0(), "rule__LhsLtRhs__Alternatives_0");
					put(grammarAccess.getLhsLtRhsAccess().getAlternatives_2(), "rule__LhsLtRhs__Alternatives_2");
					put(grammarAccess.getLhsLtRhsAccess().getAlternatives_3(), "rule__LhsLtRhs__Alternatives_3");
					put(grammarAccess.getLhsLtRhsAccess().getAlternatives_3_0_1(), "rule__LhsLtRhs__Alternatives_3_0_1");
					put(grammarAccess.getPenaltyAccess().getAlternatives_0(), "rule__Penalty__Alternatives_0");
					put(grammarAccess.getConstraintAccess().getOperatorAlternatives_1_0(), "rule__Constraint__OperatorAlternatives_1_0");
					put(grammarAccess.getModelAccess().getAlternatives_0(), "rule__Model__Alternatives_0");
					put(grammarAccess.getModelAccess().getAlternatives_3(), "rule__Model__Alternatives_3");
					put(grammarAccess.getInitialAccess().getAlternatives_0(), "rule__Initial__Alternatives_0");
					put(grammarAccess.getSequenceAccess().getAlternatives_0(), "rule__Sequence__Alternatives_0");
					put(grammarAccess.getSequenceAccess().getAlternatives_3(), "rule__Sequence__Alternatives_3");
					put(grammarAccess.getConditionAccess().getAlternatives_0(), "rule__Condition__Alternatives_0");
					put(grammarAccess.getConditionAccess().getAlternatives_1(), "rule__Condition__Alternatives_1");
					put(grammarAccess.getBinaryOpAccess().getAlternatives(), "rule__BinaryOp__Alternatives");
					put(grammarAccess.getConditionalTermAccess().getAlternatives(), "rule__ConditionalTerm__Alternatives");
					put(grammarAccess.getRelationAccess().getAlternatives(), "rule__Relation__Alternatives");
					put(grammarAccess.getAddAccess().getAlternatives_1_0(), "rule__Add__Alternatives_1_0");
					put(grammarAccess.getMultiplyAccess().getAlternatives_1_0(), "rule__Multiply__Alternatives_1_0");
					put(grammarAccess.getUnaryAccess().getAlternatives_0(), "rule__Unary__Alternatives_0");
					put(grammarAccess.getTermAccess().getAlternatives(), "rule__Term__Alternatives");
					put(grammarAccess.getFunctionAccess().getAlternatives(), "rule__Function__Alternatives");
					put(grammarAccess.getNumberAccess().getAlternatives(), "rule__Number__Alternatives");
					put(grammarAccess.getIncludeFileAccess().getAlternatives_0(), "rule__IncludeFile__Alternatives_0");
					put(grammarAccess.getIncludeFileAccess().getLocalAlternatives_1_1_0(), "rule__IncludeFile__LocalAlternatives_1_1_0");
					put(grammarAccess.getIncludeModelAccess().getAlternatives_0(), "rule__IncludeModel__Alternatives_0");
					put(grammarAccess.getIncludeModelAccess().getAlternatives_1(), "rule__IncludeModel__Alternatives_1");
					put(grammarAccess.getWreslEvaluatorAccess().getGroup_1(), "rule__WreslEvaluator__Group_1__0");
					put(grammarAccess.getIfIncIitemsAccess().getGroup(), "rule__IfIncIitems__Group__0");
					put(grammarAccess.getIfTermAccess().getGroup(), "rule__IfTerm__Group__0");
					put(grammarAccess.getElseIfTermAccess().getGroup(), "rule__ElseIfTerm__Group__0");
					put(grammarAccess.getElseTermAccess().getGroup(), "rule__ElseTerm__Group__0");
					put(grammarAccess.getObjectiveAccess().getGroup(), "rule__Objective__Group__0");
					put(grammarAccess.getObjectiveAccess().getGroup_1(), "rule__Objective__Group_1__0");
					put(grammarAccess.getWeightItemAccess().getGroup(), "rule__WeightItem__Group__0");
					put(grammarAccess.getDefineAccess().getGroup(), "rule__Define__Group__0");
					put(grammarAccess.getDefineAccess().getGroup_1(), "rule__Define__Group_1__0");
					put(grammarAccess.getSvarDefAccess().getGroup(), "rule__SvarDef__Group__0");
					put(grammarAccess.getSvarDefAccess().getGroup_1(), "rule__SvarDef__Group_1__0");
					put(grammarAccess.getDvarDefAccess().getGroup(), "rule__DvarDef__Group__0");
					put(grammarAccess.getDvarDefAccess().getGroup_1(), "rule__DvarDef__Group_1__0");
					put(grammarAccess.getConstDefAccess().getGroup(), "rule__ConstDef__Group__0");
					put(grammarAccess.getConstDefAccess().getGroup_1(), "rule__ConstDef__Group_1__0");
					put(grammarAccess.getExternalAccess().getGroup(), "rule__External__Group__0");
					put(grammarAccess.getExternalAccess().getGroup_1_0(), "rule__External__Group_1_0__0");
					put(grammarAccess.getExternalAccess().getGroup_1_1(), "rule__External__Group_1_1__0");
					put(grammarAccess.getAliasAccess().getGroup(), "rule__Alias__Group__0");
					put(grammarAccess.getAliasAccess().getGroup_1(), "rule__Alias__Group_1__0");
					put(grammarAccess.getAliasAccess().getGroup_6(), "rule__Alias__Group_6__0");
					put(grammarAccess.getAliasAccess().getGroup_7(), "rule__Alias__Group_7__0");
					put(grammarAccess.getDVarNonStdAccess().getGroup(), "rule__DVarNonStd__Group__0");
					put(grammarAccess.getDVarStdAccess().getGroup(), "rule__DVarStd__Group__0");
					put(grammarAccess.getDVarIntegerStdAccess().getGroup(), "rule__DVarIntegerStd__Group__0");
					put(grammarAccess.getDVarIntegerNonStdAccess().getGroup(), "rule__DVarIntegerNonStd__Group__0");
					put(grammarAccess.getSVarDSSAccess().getGroup(), "rule__SVarDSS__Group__0");
					put(grammarAccess.getSVarDSSAccess().getGroup_6(), "rule__SVarDSS__Group_6__0");
					put(grammarAccess.getSVarExpressionAccess().getGroup(), "rule__SVarExpression__Group__0");
					put(grammarAccess.getCaseContentAccess().getGroup(), "rule__CaseContent__Group__0");
					put(grammarAccess.getSumContentAccess().getGroup(), "rule__SumContent__Group__0");
					put(grammarAccess.getSumHeaderAccess().getGroup(), "rule__SumHeader__Group__0");
					put(grammarAccess.getSumHeaderAccess().getGroup_5(), "rule__SumHeader__Group_5__0");
					put(grammarAccess.getValueContentAccess().getGroup(), "rule__ValueContent__Group__0");
					put(grammarAccess.getTableContentAccess().getGroup(), "rule__TableContent__Group__0");
					put(grammarAccess.getTableContentAccess().getGroup_4(), "rule__TableContent__Group_4__0");
					put(grammarAccess.getTableContentAccess().getGroup_5(), "rule__TableContent__Group_5__0");
					put(grammarAccess.getWhereItemsAccess().getGroup(), "rule__WhereItems__Group__0");
					put(grammarAccess.getWhereItemsAccess().getGroup_1(), "rule__WhereItems__Group_1__0");
					put(grammarAccess.getAssignmentAccess().getGroup(), "rule__Assignment__Group__0");
					put(grammarAccess.getUpperLowerAccess().getGroup(), "rule__UpperLower__Group__0");
					put(grammarAccess.getLowerUpperAccess().getGroup(), "rule__LowerUpper__Group__0");
					put(grammarAccess.getUpperAccess().getGroup(), "rule__Upper__Group__0");
					put(grammarAccess.getUpperAccess().getGroup_1_0(), "rule__Upper__Group_1_0__0");
					put(grammarAccess.getLowerAccess().getGroup(), "rule__Lower__Group__0");
					put(grammarAccess.getLowerAccess().getGroup_1_0(), "rule__Lower__Group_1_0__0");
					put(grammarAccess.getGoalAccess().getGroup(), "rule__Goal__Group__0");
					put(grammarAccess.getGoalAccess().getGroup_1(), "rule__Goal__Group_1__0");
					put(grammarAccess.getGoalCaseAccess().getGroup(), "rule__GoalCase__Group__0");
					put(grammarAccess.getGoalCaseContentAccess().getGroup(), "rule__GoalCaseContent__Group__0");
					put(grammarAccess.getGoalNoCaseContentAccess().getGroup(), "rule__GoalNoCaseContent__Group__0");
					put(grammarAccess.getSubContentAccess().getGroup_0(), "rule__SubContent__Group_0__0");
					put(grammarAccess.getSubContentAccess().getGroup_1(), "rule__SubContent__Group_1__0");
					put(grammarAccess.getLhsGtRhsAccess().getGroup(), "rule__LhsGtRhs__Group__0");
					put(grammarAccess.getLhsGtRhsAccess().getGroup_3_0(), "rule__LhsGtRhs__Group_3_0__0");
					put(grammarAccess.getLhsLtRhsAccess().getGroup(), "rule__LhsLtRhs__Group__0");
					put(grammarAccess.getLhsLtRhsAccess().getGroup_3_0(), "rule__LhsLtRhs__Group_3_0__0");
					put(grammarAccess.getPenaltyAccess().getGroup(), "rule__Penalty__Group__0");
					put(grammarAccess.getConstraintAccess().getGroup(), "rule__Constraint__Group__0");
					put(grammarAccess.getModelAccess().getGroup(), "rule__Model__Group__0");
					put(grammarAccess.getInitialAccess().getGroup(), "rule__Initial__Group__0");
					put(grammarAccess.getSequenceAccess().getGroup(), "rule__Sequence__Group__0");
					put(grammarAccess.getSequenceAccess().getGroup_6(), "rule__Sequence__Group_6__0");
					put(grammarAccess.getConditionAccess().getGroup(), "rule__Condition__Group__0");
					put(grammarAccess.getConditionAccess().getGroup_1_1(), "rule__Condition__Group_1_1__0");
					put(grammarAccess.getLogicalExpressionAccess().getGroup(), "rule__LogicalExpression__Group__0");
					put(grammarAccess.getLogicalExpressionAccess().getGroup_1(), "rule__LogicalExpression__Group_1__0");
					put(grammarAccess.getConditionalUnaryAccess().getGroup(), "rule__ConditionalUnary__Group__0");
					put(grammarAccess.getConditionalTermAccess().getGroup_0(), "rule__ConditionalTerm__Group_0__0");
					put(grammarAccess.getAddAccess().getGroup(), "rule__Add__Group__0");
					put(grammarAccess.getAddAccess().getGroup_1(), "rule__Add__Group_1__0");
					put(grammarAccess.getMultiplyAccess().getGroup(), "rule__Multiply__Group__0");
					put(grammarAccess.getMultiplyAccess().getGroup_1(), "rule__Multiply__Group_1__0");
					put(grammarAccess.getUnaryAccess().getGroup(), "rule__Unary__Group__0");
					put(grammarAccess.getTermAccess().getGroup_3(), "rule__Term__Group_3__0");
					put(grammarAccess.getExternalFunctionAccess().getGroup(), "rule__ExternalFunction__Group__0");
					put(grammarAccess.getExternalFunctionAccess().getGroup_3(), "rule__ExternalFunction__Group_3__0");
					put(grammarAccess.getMaxFunctionAccess().getGroup(), "rule__MaxFunction__Group__0");
					put(grammarAccess.getMaxFunctionAccess().getGroup_3(), "rule__MaxFunction__Group_3__0");
					put(grammarAccess.getMinFunctionAccess().getGroup(), "rule__MinFunction__Group__0");
					put(grammarAccess.getMinFunctionAccess().getGroup_3(), "rule__MinFunction__Group_3__0");
					put(grammarAccess.getIntFunctionAccess().getGroup(), "rule__IntFunction__Group__0");
					put(grammarAccess.getVarModelAccess().getGroup(), "rule__VarModel__Group__0");
					put(grammarAccess.getRangeFunctionAccess().getGroup(), "rule__RangeFunction__Group__0");
					put(grammarAccess.getIncludeFileAccess().getGroup(), "rule__IncludeFile__Group__0");
					put(grammarAccess.getIncludeFileAccess().getGroup_1(), "rule__IncludeFile__Group_1__0");
					put(grammarAccess.getIncludeModelAccess().getGroup(), "rule__IncludeModel__Group__0");
					put(grammarAccess.getWreslEvaluatorAccess().getPatternAssignment_0_0(), "rule__WreslEvaluator__PatternAssignment_0_0");
					put(grammarAccess.getWreslEvaluatorAccess().getIfincitemAssignment_0_1(), "rule__WreslEvaluator__IfincitemAssignment_0_1");
					put(grammarAccess.getWreslEvaluatorAccess().getInitialAssignment_1_0(), "rule__WreslEvaluator__InitialAssignment_1_0");
					put(grammarAccess.getWreslEvaluatorAccess().getSequenceAssignment_1_1(), "rule__WreslEvaluator__SequenceAssignment_1_1");
					put(grammarAccess.getWreslEvaluatorAccess().getModelAssignment_1_2(), "rule__WreslEvaluator__ModelAssignment_1_2");
					put(grammarAccess.getIfIncIitemsAccess().getIftermAssignment_0(), "rule__IfIncIitems__IftermAssignment_0");
					put(grammarAccess.getIfIncIitemsAccess().getElseiftermAssignment_1(), "rule__IfIncIitems__ElseiftermAssignment_1");
					put(grammarAccess.getIfIncIitemsAccess().getElsetermAssignment_2(), "rule__IfIncIitems__ElsetermAssignment_2");
					put(grammarAccess.getIfTermAccess().getLogicalAssignment_1(), "rule__IfTerm__LogicalAssignment_1");
					put(grammarAccess.getIfTermAccess().getPatternAssignment_3(), "rule__IfTerm__PatternAssignment_3");
					put(grammarAccess.getElseIfTermAccess().getLogicalAssignment_1(), "rule__ElseIfTerm__LogicalAssignment_1");
					put(grammarAccess.getElseIfTermAccess().getPatternAssignment_3(), "rule__ElseIfTerm__PatternAssignment_3");
					put(grammarAccess.getElseTermAccess().getPatternAssignment_2(), "rule__ElseTerm__PatternAssignment_2");
					put(grammarAccess.getObjectiveAccess().getLocalAssignment_1_1(), "rule__Objective__LocalAssignment_1_1");
					put(grammarAccess.getObjectiveAccess().getNameAssignment_2(), "rule__Objective__NameAssignment_2");
					put(grammarAccess.getObjectiveAccess().getWeightsAssignment_5(), "rule__Objective__WeightsAssignment_5");
					put(grammarAccess.getWeightItemAccess().getNameAssignment_1(), "rule__WeightItem__NameAssignment_1");
					put(grammarAccess.getWeightItemAccess().getExpressionAssignment_3(), "rule__WeightItem__ExpressionAssignment_3");
					put(grammarAccess.getDefineAccess().getLocalAssignment_1_1(), "rule__Define__LocalAssignment_1_1");
					put(grammarAccess.getDefineAccess().getNameAssignment_2(), "rule__Define__NameAssignment_2");
					put(grammarAccess.getDefineAccess().getDefinitionAssignment_4(), "rule__Define__DefinitionAssignment_4");
					put(grammarAccess.getSvarDefAccess().getLocalAssignment_1_1(), "rule__SvarDef__LocalAssignment_1_1");
					put(grammarAccess.getSvarDefAccess().getNameAssignment_2(), "rule__SvarDef__NameAssignment_2");
					put(grammarAccess.getSvarDefAccess().getDefinitionAssignment_4(), "rule__SvarDef__DefinitionAssignment_4");
					put(grammarAccess.getDvarDefAccess().getLocalAssignment_1_1(), "rule__DvarDef__LocalAssignment_1_1");
					put(grammarAccess.getDvarDefAccess().getNameAssignment_2(), "rule__DvarDef__NameAssignment_2");
					put(grammarAccess.getDvarDefAccess().getDefinitionAssignment_4(), "rule__DvarDef__DefinitionAssignment_4");
					put(grammarAccess.getConstDefAccess().getLocalAssignment_1_1(), "rule__ConstDef__LocalAssignment_1_1");
					put(grammarAccess.getConstDefAccess().getNameAssignment_2(), "rule__ConstDef__NameAssignment_2");
					put(grammarAccess.getConstDefAccess().getDefinitionAssignment_4(), "rule__ConstDef__DefinitionAssignment_4");
					put(grammarAccess.getExternalAccess().getNameAssignment_1_0_0(), "rule__External__NameAssignment_1_0_0");
					put(grammarAccess.getAliasAccess().getLocalAssignment_1_1(), "rule__Alias__LocalAssignment_1_1");
					put(grammarAccess.getAliasAccess().getNameAssignment_2(), "rule__Alias__NameAssignment_2");
					put(grammarAccess.getAliasAccess().getExpressionAssignment_5(), "rule__Alias__ExpressionAssignment_5");
					put(grammarAccess.getAliasAccess().getKindAssignment_6_1(), "rule__Alias__KindAssignment_6_1");
					put(grammarAccess.getAliasAccess().getUnitsAssignment_7_1(), "rule__Alias__UnitsAssignment_7_1");
					put(grammarAccess.getDVarNonStdAccess().getLowerUpperAssignment_0(), "rule__DVarNonStd__LowerUpperAssignment_0");
					put(grammarAccess.getDVarNonStdAccess().getKindAssignment_2(), "rule__DVarNonStd__KindAssignment_2");
					put(grammarAccess.getDVarNonStdAccess().getUnitsAssignment_4(), "rule__DVarNonStd__UnitsAssignment_4");
					put(grammarAccess.getDVarStdAccess().getKindAssignment_2(), "rule__DVarStd__KindAssignment_2");
					put(grammarAccess.getDVarStdAccess().getUnitsAssignment_4(), "rule__DVarStd__UnitsAssignment_4");
					put(grammarAccess.getDVarIntegerStdAccess().getKindAssignment_3(), "rule__DVarIntegerStd__KindAssignment_3");
					put(grammarAccess.getDVarIntegerStdAccess().getUnitsAssignment_5(), "rule__DVarIntegerStd__UnitsAssignment_5");
					put(grammarAccess.getDVarIntegerNonStdAccess().getKindAssignment_3(), "rule__DVarIntegerNonStd__KindAssignment_3");
					put(grammarAccess.getDVarIntegerNonStdAccess().getUnitsAssignment_5(), "rule__DVarIntegerNonStd__UnitsAssignment_5");
					put(grammarAccess.getSVarDSSAccess().getBPartAssignment_1(), "rule__SVarDSS__BPartAssignment_1");
					put(grammarAccess.getSVarDSSAccess().getKindAssignment_3(), "rule__SVarDSS__KindAssignment_3");
					put(grammarAccess.getSVarDSSAccess().getUnitsAssignment_5(), "rule__SVarDSS__UnitsAssignment_5");
					put(grammarAccess.getSVarDSSAccess().getConvertAssignment_6_1(), "rule__SVarDSS__ConvertAssignment_6_1");
					put(grammarAccess.getSVarExpressionAccess().getExpressionAssignment_1(), "rule__SVarExpression__ExpressionAssignment_1");
					put(grammarAccess.getSVarSumAccess().getSumContentAssignment(), "rule__SVarSum__SumContentAssignment");
					put(grammarAccess.getSVarTableAccess().getTableContentAssignment(), "rule__SVarTable__TableContentAssignment");
					put(grammarAccess.getSVarCaseAccess().getCaseContentAssignment(), "rule__SVarCase__CaseContentAssignment");
					put(grammarAccess.getCaseContentAccess().getCaseNameAssignment_1(), "rule__CaseContent__CaseNameAssignment_1");
					put(grammarAccess.getCaseContentAccess().getConditionAssignment_3(), "rule__CaseContent__ConditionAssignment_3");
					put(grammarAccess.getCaseContentAccess().getContentAssignment_4_0(), "rule__CaseContent__ContentAssignment_4_0");
					put(grammarAccess.getCaseContentAccess().getContentAssignment_4_1(), "rule__CaseContent__ContentAssignment_4_1");
					put(grammarAccess.getCaseContentAccess().getContentAssignment_4_2(), "rule__CaseContent__ContentAssignment_4_2");
					put(grammarAccess.getSumContentAccess().getHeaderAssignment_1(), "rule__SumContent__HeaderAssignment_1");
					put(grammarAccess.getSumContentAccess().getExpressionAssignment_2(), "rule__SumContent__ExpressionAssignment_2");
					put(grammarAccess.getSumHeaderAccess().getExpression1Assignment_2(), "rule__SumHeader__Expression1Assignment_2");
					put(grammarAccess.getSumHeaderAccess().getExpression2Assignment_4(), "rule__SumHeader__Expression2Assignment_4");
					put(grammarAccess.getValueContentAccess().getExpressionAssignment_1(), "rule__ValueContent__ExpressionAssignment_1");
					put(grammarAccess.getTableContentAccess().getTableNameAssignment_1(), "rule__TableContent__TableNameAssignment_1");
					put(grammarAccess.getTableContentAccess().getFromAssignment_3(), "rule__TableContent__FromAssignment_3");
					put(grammarAccess.getTableContentAccess().getGivenAssignment_4_1(), "rule__TableContent__GivenAssignment_4_1");
					put(grammarAccess.getTableContentAccess().getUseAssignment_4_3(), "rule__TableContent__UseAssignment_4_3");
					put(grammarAccess.getTableContentAccess().getWhereAssignment_5_1(), "rule__TableContent__WhereAssignment_5_1");
					put(grammarAccess.getWhereItemsAccess().getAssignmentAssignment_0(), "rule__WhereItems__AssignmentAssignment_0");
					put(grammarAccess.getWhereItemsAccess().getAssignmentAssignment_1_1(), "rule__WhereItems__AssignmentAssignment_1_1");
					put(grammarAccess.getAssignmentAccess().getTermAssignment_0(), "rule__Assignment__TermAssignment_0");
					put(grammarAccess.getAssignmentAccess().getExpressionAssignment_2(), "rule__Assignment__ExpressionAssignment_2");
					put(grammarAccess.getUpperLowerAccess().getUpperAssignment_0(), "rule__UpperLower__UpperAssignment_0");
					put(grammarAccess.getUpperLowerAccess().getLowerAssignment_1(), "rule__UpperLower__LowerAssignment_1");
					put(grammarAccess.getLowerUpperAccess().getLowerAssignment_0(), "rule__LowerUpper__LowerAssignment_0");
					put(grammarAccess.getLowerUpperAccess().getUpperAssignment_1(), "rule__LowerUpper__UpperAssignment_1");
					put(grammarAccess.getUpperAccess().getExpressionAssignment_1_1(), "rule__Upper__ExpressionAssignment_1_1");
					put(grammarAccess.getLowerAccess().getExpressionAssignment_1_1(), "rule__Lower__ExpressionAssignment_1_1");
					put(grammarAccess.getGoalAccess().getLocalAssignment_1_1(), "rule__Goal__LocalAssignment_1_1");
					put(grammarAccess.getGoalAccess().getNameAssignment_2(), "rule__Goal__NameAssignment_2");
					put(grammarAccess.getGoalAccess().getDefinitionAssignment_4(), "rule__Goal__DefinitionAssignment_4");
					put(grammarAccess.getGoalCaseAccess().getLhsAssignment_1(), "rule__GoalCase__LhsAssignment_1");
					put(grammarAccess.getGoalCaseAccess().getContentAssignment_2_0(), "rule__GoalCase__ContentAssignment_2_0");
					put(grammarAccess.getGoalCaseAccess().getCaseContentAssignment_2_1(), "rule__GoalCase__CaseContentAssignment_2_1");
					put(grammarAccess.getGoalCaseContentAccess().getCaseNameAssignment_1(), "rule__GoalCaseContent__CaseNameAssignment_1");
					put(grammarAccess.getGoalCaseContentAccess().getConditionAssignment_3(), "rule__GoalCaseContent__ConditionAssignment_3");
					put(grammarAccess.getGoalCaseContentAccess().getRhsAssignment_5(), "rule__GoalCaseContent__RhsAssignment_5");
					put(grammarAccess.getGoalCaseContentAccess().getSubContentAssignment_6(), "rule__GoalCaseContent__SubContentAssignment_6");
					put(grammarAccess.getGoalNoCaseContentAccess().getRhsAssignment_1(), "rule__GoalNoCaseContent__RhsAssignment_1");
					put(grammarAccess.getGoalNoCaseContentAccess().getSubContentAssignment_2(), "rule__GoalNoCaseContent__SubContentAssignment_2");
					put(grammarAccess.getSubContentAccess().getGtAssignment_0_0(), "rule__SubContent__GtAssignment_0_0");
					put(grammarAccess.getSubContentAccess().getLtAssignment_0_1(), "rule__SubContent__LtAssignment_0_1");
					put(grammarAccess.getSubContentAccess().getLtAssignment_1_0(), "rule__SubContent__LtAssignment_1_0");
					put(grammarAccess.getSubContentAccess().getGtAssignment_1_1(), "rule__SubContent__GtAssignment_1_1");
					put(grammarAccess.getLhsGtRhsAccess().getPenaltyAssignment_3_1(), "rule__LhsGtRhs__PenaltyAssignment_3_1");
					put(grammarAccess.getLhsLtRhsAccess().getPenaltyAssignment_3_1(), "rule__LhsLtRhs__PenaltyAssignment_3_1");
					put(grammarAccess.getPenaltyAccess().getExpressionAssignment_1(), "rule__Penalty__ExpressionAssignment_1");
					put(grammarAccess.getGoalSimpleAccess().getConstraintAssignment(), "rule__GoalSimple__ConstraintAssignment");
					put(grammarAccess.getConstraintAccess().getLhsAssignment_0(), "rule__Constraint__LhsAssignment_0");
					put(grammarAccess.getConstraintAccess().getOperatorAssignment_1(), "rule__Constraint__OperatorAssignment_1");
					put(grammarAccess.getConstraintAccess().getRhsAssignment_2(), "rule__Constraint__RhsAssignment_2");
					put(grammarAccess.getModelAccess().getNameAssignment_1(), "rule__Model__NameAssignment_1");
					put(grammarAccess.getModelAccess().getPatternAssignment_3_0(), "rule__Model__PatternAssignment_3_0");
					put(grammarAccess.getModelAccess().getIfincitemsAssignment_3_1(), "rule__Model__IfincitemsAssignment_3_1");
					put(grammarAccess.getInitialAccess().getPatternAssignment_2(), "rule__Initial__PatternAssignment_2");
					put(grammarAccess.getSequenceAccess().getNameAssignment_1(), "rule__Sequence__NameAssignment_1");
					put(grammarAccess.getSequenceAccess().getModelAssignment_4(), "rule__Sequence__ModelAssignment_4");
					put(grammarAccess.getSequenceAccess().getConditionAssignment_5(), "rule__Sequence__ConditionAssignment_5");
					put(grammarAccess.getSequenceAccess().getOrderAssignment_6_1(), "rule__Sequence__OrderAssignment_6_1");
					put(grammarAccess.getConditionAccess().getLogicalAssignment_1_0(), "rule__Condition__LogicalAssignment_1_0");
					put(grammarAccess.getLogicalExpressionAccess().getC1Assignment_0(), "rule__LogicalExpression__C1Assignment_0");
					put(grammarAccess.getLogicalExpressionAccess().getC2Assignment_1_1(), "rule__LogicalExpression__C2Assignment_1_1");
					put(grammarAccess.getConditionalTermAccess().getE1Assignment_0_0(), "rule__ConditionalTerm__E1Assignment_0_0");
					put(grammarAccess.getConditionalTermAccess().getE2Assignment_0_2(), "rule__ConditionalTerm__E2Assignment_0_2");
					put(grammarAccess.getAddAccess().getM1Assignment_0(), "rule__Add__M1Assignment_0");
					put(grammarAccess.getAddAccess().getM2Assignment_1_1(), "rule__Add__M2Assignment_1_1");
					put(grammarAccess.getMultiplyAccess().getU1Assignment_0(), "rule__Multiply__U1Assignment_0");
					put(grammarAccess.getMultiplyAccess().getU2Assignment_1_1(), "rule__Multiply__U2Assignment_1_1");
					put(grammarAccess.getTermAccess().getIAssignment_0(), "rule__Term__IAssignment_0");
					put(grammarAccess.getTermAccess().getNAssignment_1(), "rule__Term__NAssignment_1");
					put(grammarAccess.getTermAccess().getFAssignment_2(), "rule__Term__FAssignment_2");
					put(grammarAccess.getTermAccess().getE2Assignment_3_1(), "rule__Term__E2Assignment_3_1");
					put(grammarAccess.getExternalFunctionAccess().getE1Assignment_2(), "rule__ExternalFunction__E1Assignment_2");
					put(grammarAccess.getExternalFunctionAccess().getE2Assignment_3_1(), "rule__ExternalFunction__E2Assignment_3_1");
					put(grammarAccess.getMaxFunctionAccess().getE1Assignment_2(), "rule__MaxFunction__E1Assignment_2");
					put(grammarAccess.getMaxFunctionAccess().getE2Assignment_3_1(), "rule__MaxFunction__E2Assignment_3_1");
					put(grammarAccess.getMinFunctionAccess().getE1Assignment_2(), "rule__MinFunction__E1Assignment_2");
					put(grammarAccess.getMinFunctionAccess().getE2Assignment_3_1(), "rule__MinFunction__E2Assignment_3_1");
					put(grammarAccess.getIntFunctionAccess().getEAssignment_2(), "rule__IntFunction__EAssignment_2");
					put(grammarAccess.getIdentAccess().getNameAssignment(), "rule__Ident__NameAssignment");
					put(grammarAccess.getIncludeFileAccess().getLocalAssignment_1_1(), "rule__IncludeFile__LocalAssignment_1_1");
					put(grammarAccess.getIncludeFileAccess().getFileAssignment_2(), "rule__IncludeFile__FileAssignment_2");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			gov.ca.dwr.wresl.xtext.editor.ui.contentassist.antlr.internal.InternalWreslEditorParser typedParser = (gov.ca.dwr.wresl.xtext.editor.ui.contentassist.antlr.internal.InternalWreslEditorParser) parser;
			typedParser.entryRuleWreslEvaluator();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public WreslEditorGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(WreslEditorGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
