package me.a8kj.battlestreaks.action;

public interface Action<U> {

    void execute(U u);

    ActionResult getResult();

    enum ActionResult {
        EXECUTED, NOT_EXECUTED, ERROR;
    }
}
