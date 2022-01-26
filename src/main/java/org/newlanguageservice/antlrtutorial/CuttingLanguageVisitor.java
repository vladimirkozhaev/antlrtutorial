package org.newlanguageservice.antlrtutorial;

import java.util.HashMap;
import java.util.Map;

import org.newlanguageservice.ch1.CuttingLanguageBaseVisitor;
import org.newlanguageservice.ch1.CuttingLanguageParser.Add_or_subContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.Brackets_expressionContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.Int_valContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.LineToContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.MoveToContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.Mul_div_expressionContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.PointContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.Variable_definitionContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.Variable_equalContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.Variable_refContext;

import javafx.scene.canvas.GraphicsContext;

public class CuttingLanguageVisitor extends CuttingLanguageBaseVisitor<ExprResult> {
	private Point point;
	private GraphicsContext gc;
	private Map<String, Variable> variables = new HashMap<>();

	public CuttingLanguageVisitor(Point point, GraphicsContext gc) {

		this.point = point;
		this.gc = gc;
	}

	@Override
	public ExprResult visitInt_val(Int_valContext ctx) {
		return new ExprResult(Type.NUMBER, Integer.parseInt(ctx.getText()));
	}

	@Override
	public ExprResult visitPoint(PointContext ctx) {
		Integer x = (Integer) visit(ctx.x).getResult();
		Integer y = (Integer) visit(ctx.y).getResult();

		return new ExprResult(Type.POINT, new Point(x, y));
	}

	@Override
	public ExprResult visitVariable_definition(Variable_definitionContext ctx) {
		Variable variable = new Variable(ctx.variableName.getText());
		Type type = ctx.type.getText().equals("int") ? Type.NUMBER : Type.POINT;
		variable.setType(type);

		if (ctx.expr != null) {
			ExprResult def = visit(ctx.expr);
			if (def.getType() != variable.getType()) {
				throw new LanguageException(ctx,
						"variable type don't correspond with an expression:" + variable.getName());
			}
			variable.setValue(def);
		}
		variables.put(variable.getName(), variable);
		return new ExprResult(Type.VOID, null);
	}

	@Override
	public ExprResult visitVariable_equal(Variable_equalContext ctx) {
		String variableName = ctx.variable_ref().getText();
		if (!this.variables.containsKey(variableName)) {
			throw new LanguageException(ctx, "variable " + variableName + " is not yet defined");
		}
		Variable variable = variables.get(variableName);
		variables.put(variableName, variable);
		ExprResult result = visit(ctx.expr);
		if (result.getType() != variable.getType()) {

			throw new LanguageException(ctx, "Type " + variable.getType() + " of variable " + variableName
					+ " is not correspond with an expression type " + result.getType());
		}

		return new ExprResult(Type.VOID, null);
	}

	@Override
	public ExprResult visitMul_div_expression(Mul_div_expressionContext ctx) {
		ExprResult left = visit(ctx.add_or_sub());

		if (ctx.mul_div_expression() != null) {
			ExprResult right = visit(ctx.mul_div_expression());

			if (left.getType() != Type.NUMBER || right.getType() != Type.NUMBER) {
				throw new LanguageException(ctx,
						"Type " + left + " is not correspond with an expression type " + right);
			}

			return ctx.op.getText().equals("*")
					? new ExprResult(Type.NUMBER, (Integer) left.getResult() * (Integer) left.getResult())
					: new ExprResult(Type.NUMBER, (Integer) left.getResult() / (Integer) left.getResult());

		}
		return left;

	}

	@Override
	public ExprResult visitAdd_or_sub(Add_or_subContext ctx) {

		ExprResult left = visit(ctx.sub_element());
		if (ctx.mul_div_expression() != null) {
			ExprResult right = visit(ctx.mul_div_expression());

			if (left.getType() != right.getType()) {
				throw new LanguageException(ctx,
						"Type " + left + " is not correspond with an expression type " + right);
			}

			boolean isPlus = ctx.op.getText().equals("+");
			if (left.getType() == Type.NUMBER) {
				return isPlus ? new ExprResult(Type.NUMBER, (Integer) left.getResult() + (Integer) left.getResult())
						: new ExprResult(Type.NUMBER, (Integer) left.getResult() - (Integer) left.getResult());

			}

			Point leftResult = (Point) left.getResult();
			Point rightResult = (Point) right.getResult();

			return new ExprResult(Type.POINT, isPlus
					? new Point(leftResult.getX() + rightResult.getX(), leftResult.getY() + rightResult.getY())
					: new Point(leftResult.getX() - rightResult.getX(), leftResult.getY() - rightResult.getY()));
		} else
			return left;
	}

	@Override
	public ExprResult visitBrackets_expression(Brackets_expressionContext ctx) {
		return visit(ctx.expr);
	}

	@Override
	public ExprResult visitVariable_ref(Variable_refContext ctx) {
		if (!variables.containsKey(ctx.var_name_ref.getText())) {
			throw new LanguageException(ctx, "variable with name " + ctx.getText() + " is not defined");
		}
		Variable variable = variables.get(ctx.var_name_ref.getText());

		if (variable.getValue() == null) {
			throw new LanguageException(ctx, "variable with name " + ctx.getText() + " is not instantiated");
		}

		return new ExprResult(variable.getType(), ((ExprResult)variable.getValue()).getResult());
	}

	@Override
	public ExprResult visitMoveTo(MoveToContext ctx) {

		ExprResult pointResult = visit(ctx.point());
		point = (Point) pointResult.getResult();
		if (gc != null) {
			gc.moveTo(point.getX(), point.getY());

			gc.save();

		}
		return new ExprResult(Type.POINT, point);
	}

	@Override
	public ExprResult visitLineTo(LineToContext ctx) {
		ExprResult pointResult = visit(ctx.point());
		int x = point.getX();
		int y = point.getY();
		point = (Point) pointResult.getResult();
		if (gc != null) {
			gc.strokeLine(x, y, point.getX(), point.getY());
			gc.save();
		}
		return new ExprResult(Type.POINT, point);
	}
}
