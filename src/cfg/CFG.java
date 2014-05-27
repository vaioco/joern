package cfg;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

// The first node added is the entry node
// The last node added is the exitNode
// For nodes with two outgoing edges,
// the first edge is the one taken if
// condition evaluates to true.

public class CFG {

    Vector<CFGNode> statements = new Vector<CFGNode>();

    Edges<CFGEdge, CFGNode> edges = new Edges<CFGEdge, CFGNode>();
    SwitchLabels switchLabels = new SwitchLabels();

    Vector<CFGNode> jumpStatements = new Vector<CFGNode>();
    HashMap<String, CFGNode> labels = new HashMap<String, CFGNode>();

    HashMap<CFGNode, CFGNode> loopStart = new HashMap<CFGNode, CFGNode>();


    public void addCFG(CFG otherCFG) {
	if (statements.size() == 0) {
	    replaceCFGBy(otherCFG);
	    return;
	}

	Vector<CFGNode> otherBlocks = otherCFG.getStatements();
	Edges<CFGEdge, CFGNode> otherEdges = otherCFG.getEdges();
	switchLabels.addMultiHashMap(otherCFG.getSwitchLabels());
	statements.addAll(otherBlocks);
	edges.addEdges(otherEdges);

	jumpStatements.addAll(otherCFG.getJumpStatements());
	labels.putAll(otherCFG.getLabels());

	loopStart.putAll(otherCFG.loopStart);

    }


    public void replaceCFGBy(CFG otherCFG) {
	this.statements = otherCFG.statements;
	this.edges = otherCFG.edges;
	this.switchLabels = otherCFG.switchLabels;
	this.labels = otherCFG.labels;
	this.jumpStatements = otherCFG.jumpStatements;
	this.loopStart = otherCFG.loopStart;
    }


    public CFGNode getBlockByLabel(String label) {
	return labels.get(label);
    }


    public void addSwitchLabel(CFGNode surroundingSwitch, CFGNode labeledBlock) {
	switchLabels.add(surroundingSwitch, labeledBlock);
    }


    public CFGNode getOuterLoop(CFGNode thisStatement) {
	return loopStart.get(thisStatement);
    }


    public SwitchLabels getSwitchLabels() {
	return switchLabels;
    }


    public CFGNode getLastStatement() {
	try {
	    return statements.lastElement();
	} catch (RuntimeException ex) {
	    return null;
	}
    }


    public CFGNode getFirstStatement() {
	try {
	    return statements.firstElement();
	} catch (RuntimeException ex) {
	    return null;
	}
    }


    public void labelBlock(String label, CFGNode block) {
	labels.put(label, block);
    }


    public void addStatement(CFGNode newBlock) {
	statements.add(newBlock);
    }


    public void addEdge(CFGNode srcBlock, CFGNode dstBlock) {
	addEdge(srcBlock, dstBlock, CFGEdge.EMPTY_LABEL);
    }


    public void addEdge(CFGNode srcBlock, CFGNode dstBlock, String label) {
	CFGEdge edge = new CFGEdge(srcBlock, dstBlock, label);
	edges.addEdge(edge);
    }


    public void removeAllEdgesFrom(CFGNode srcBlock) {
	edges.removeAllEdgesFrom(srcBlock);
    }


    public List<CFGEdge> getAllEdgesFrom(CFGNode srcBlock) {
	return edges.getEdgesFrom(srcBlock);
    }


    public Iterator<CFGEdge> edgeIterator() {
	return edges.iterator();
    }


    public int getNumberOfStatements() {
	return statements.size();
    }


    public int getNumberOfEdges() {
	return edges.totalSize();
    }


    public Collection<? extends CFGNode> getJumpStatements() {
	return jumpStatements;
    }


    public HashMap<String, CFGNode> getLabels() {
	return labels;
    }


    private Edges<CFGEdge, CFGNode> getEdges() {
	return edges;
    }


    public Vector<CFGNode> getStatements() {
	return statements;
    }


    public void addJumpStatement(CFGNode block) {
	this.jumpStatements.add(block);
    }

}
