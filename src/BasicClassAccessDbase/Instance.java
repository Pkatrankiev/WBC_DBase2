package BasicClassAccessDbase;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Instance implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int instance_ID;
		private String classTable;
		private String source;
		private String command;
		private String textInstance;
		@ManyToOne
		private UsersWBC userWBC;
		private Date dateSet;
		
		
		public Instance (
				String classTable,
				String source,
				String command,
				String textInstance,
				UsersWBC userWBC,
				Date dateSet
				) {
			this.classTable = classTable;
			this.source = source;
			this.command = command;
			this.textInstance = textInstance;
			this.userWBC = userWBC;
			this.dateSet = dateSet;
		}
		
	

		public Instance () {
			super();
		}

		public int getInstance_ID() {
			return instance_ID;
		}
		public void setInstance_ID(int instance_ID) {
			this.instance_ID = instance_ID;
		}



		public String getTable() {
			return classTable;
		}

		public String getSource() {
			return source;
		}

		public String getCommand() {
			return command;
		}

		public String getTextInstance() {
			return textInstance;
		}

		public UsersWBC getUserWBC() {
			return userWBC;
		}

		public Date getDateSet() {
			return dateSet;
		}

		public void setTable(String table) {
			this.classTable = table;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public void setCommand(String command) {
			this.command = command;
		}

		public void setTextInstance(String textInstance) {
			this.textInstance = textInstance;
		}

		public void setUserWBC(UsersWBC userWBC) {
			this.userWBC = userWBC;
		}

		public void setDateSet(Date dateSet) {
			this.dateSet = dateSet;
		}
}