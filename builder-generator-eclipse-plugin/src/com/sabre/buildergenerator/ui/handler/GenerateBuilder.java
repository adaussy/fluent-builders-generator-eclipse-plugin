package com.sabre.buildergenerator.ui.handler;

import com.sabre.buildergenerator.ui.actions.GenerateBuilderAction;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;

import org.eclipse.ui.handlers.HandlerUtil;


public class GenerateBuilder extends AbstractHandler {
    private final GenerateBuilderAction generateBuilderAction;

    public GenerateBuilder() {
        generateBuilderAction = new GenerateBuilderAction();
    }

    public Object execute(ExecutionEvent aEvent) throws ExecutionException {
        IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(aEvent);

        Object firstElement = selection.getFirstElement();

        if (firstElement instanceof ICompilationUnit) {
            ICompilationUnit cu = (ICompilationUnit) firstElement;

            try {
                IType[] allTypes = cu.getTypes();

                if (allTypes != null && allTypes.length == 1) {
                    generateBuilderAction.execute(allTypes[0], null, HandlerUtil.getActiveWorkbenchWindow(aEvent));
                } else {
                    MessageDialog.openError(HandlerUtil.getActiveShell(aEvent), "Error",
                        "No top level type in supplied compilation unit");
                }
            } catch (Exception e) {
                MessageDialog.openError(HandlerUtil.getActiveShell(aEvent), "Error", e.getMessage());
            }
        } else {
            MessageDialog.openInformation(HandlerUtil.getActiveShell(aEvent), "Information",
                "Please select a Java source file");
        }

        return null;
    }
}