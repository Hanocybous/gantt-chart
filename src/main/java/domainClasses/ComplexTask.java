package domainClasses;

public final class ComplexTask extends Task{
	
	public ComplexTask(int id, String name, int mamaId) {
		super (id,name,mamaId);
	}
	
	public void addSubTask(Task subTask) {
		if(this.getStart() == -1 && this.getEnd() == -1) {
			setStart(subTask.getStart());
			setEnd(subTask.getEnd());
		}
		else  {
			if(getStart() > subTask.getStart() ) {
				setStart(subTask.getStart());
			}
			if(getEnd() < subTask.getEnd()) {
				setEnd(subTask.getEnd());
			}
		}
		addCost(subTask.getCost());
	}
}
