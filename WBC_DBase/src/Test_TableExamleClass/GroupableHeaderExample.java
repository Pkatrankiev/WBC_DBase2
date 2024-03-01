package Test_TableExamleClass;

/* (swing1.1beta3)
 *example from 
 http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html 
 *
 */

/* (swing1.1beta3)
 *
 * |-----------------------------------------------------|
 * |        |       Name      |         Language         |
 * |        |-----------------|--------------------------|
 * |  SNo.  |        |        |        |      Others     |
 * |        |   1    |    2   | Native |-----------------|
 * |        |        |        |        |   2    |   3    |  
 * |-----------------------------------------------------|
 * |        |        |        |        |        |        |
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import java.util.*;

import javax.swing.plaf.basic.*;



/**
 * @version 1.0 11/09/98
 */
@SuppressWarnings("serial")
public class GroupableHeaderExample extends JFrame {

  GroupableHeaderExample() {
    super( "Groupable Header Example" );

    DefaultTableModel dm = new DefaultTableModel();
    dm.setDataVector(new Object[][]{
      {"119","foo","bar","ja","ko","zh"},
      {"911","bar","foo","en","fr","pt"}},
    new Object[]{"SNo.","1","2","Native","2","3"});

    JTable table = new JTable( dm ) {
      protected JTableHeader createDefaultTableHeader() {
          return new GroupableTableHeader(columnModel);
      }
    };
    
    
    TableColumnModel cm = table.getColumnModel();
    ColumnGroup g_name = new ColumnGroup("Name");
    g_name.add(cm.getColumn(1));
    g_name.add(cm.getColumn(2));
    ColumnGroup g_lang = new ColumnGroup("Language");
    g_lang.add(cm.getColumn(3));
    ColumnGroup g_other = new ColumnGroup("Others");
    g_other.add(cm.getColumn(4));
    g_other.add(cm.getColumn(5));
    g_lang.add(g_other);
    
    GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
    header.addColumnGroup(g_name);
    header.addColumnGroup(g_lang);
    JScrollPane scroll = new JScrollPane( table );
    getContentPane().add( scroll );
    setSize( 400, 120 );   
  }

  public static void main(String[] args) {
    GroupableHeaderExample frame = new GroupableHeaderExample();
    frame.addWindowListener( new WindowAdapter() {
      public void windowClosing( WindowEvent e ) {
  System.exit(0);
      }
    });
    frame.setVisible(true);
  }
}

/*
 * (swing1.1beta3)
 * 
 */


 

/**
  * GroupableTableHeader
  *
  * @version 1.0 10/20/98
  * @author Nobuo Tamemasa
  */

 @SuppressWarnings("serial")
class GroupableTableHeader extends JTableHeader {
  @SuppressWarnings("unused")
private static final String uiClassID = "GroupableTableHeaderUI";
  @SuppressWarnings("rawtypes")
protected Vector columnGroups = null;
    
  public GroupableTableHeader(TableColumnModel model) {
    super(model);
    setUI(new GroupableTableHeaderUI());
    setReorderingAllowed(false);
  }
  public void updateUI(){
   setUI(new GroupableTableHeaderUI());
  }
  
  public void setReorderingAllowed(boolean b) {
    reorderingAllowed = false;
  }
    
  @SuppressWarnings({ "unchecked", "rawtypes" })
public void addColumnGroup(ColumnGroup g) {
    if (columnGroups == null) {
      columnGroups = new Vector();
    }
    columnGroups.addElement(g);
  }

  @SuppressWarnings("rawtypes")
public Enumeration getColumnGroups(TableColumn col) {
    if (columnGroups == null) return null;
    Enumeration e = columnGroups.elements();
    while (e.hasMoreElements()) {
      ColumnGroup cGroup = (ColumnGroup)e.nextElement();
      Vector v_ret = (Vector)cGroup.getColumnGroups(col,new Vector());
      if (v_ret != null) { 
  return v_ret.elements();
      }
    }
    return null;
  }
  
  @SuppressWarnings("rawtypes")
public void setColumnMargin() {
    if (columnGroups == null) return;
    int columnMargin = getColumnModel().getColumnMargin();
    Enumeration e = columnGroups.elements();
    while (e.hasMoreElements()) {
      ColumnGroup cGroup = (ColumnGroup)e.nextElement();
      cGroup.setColumnMargin(columnMargin);
    }
  }
  
}
 
 
 

/*
 * (swing1.1beta3)
 * 
 */



class GroupableTableHeaderUI extends BasicTableHeaderUI {
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
public void paint(Graphics g, JComponent c) {
    Rectangle clipBounds = g.getClipBounds();
    if (header.getColumnModel() == null) return;
    ((GroupableTableHeader)header).setColumnMargin();
    int column = 0;
    Dimension size = header.getSize();
    Rectangle cellRect  = new Rectangle(0, 0, size.width, size.height);
    Hashtable h = new Hashtable();
    int columnMargin = header.getColumnModel().getColumnMargin();
    
    Enumeration enumeration = header.getColumnModel().getColumns();
    while (enumeration.hasMoreElements()) {
      cellRect.height = size.height;
      cellRect.y      = 0;
      TableColumn aColumn = (TableColumn)enumeration.nextElement();
      Enumeration cGroups = ((GroupableTableHeader)header).getColumnGroups(aColumn);
      if (cGroups != null) {
        int groupHeight = 0;
        while (cGroups.hasMoreElements()) {
          ColumnGroup cGroup = (ColumnGroup)cGroups.nextElement();
          Rectangle groupRect = (Rectangle)h.get(cGroup);
          if (groupRect == null) {
            groupRect = new Rectangle(cellRect);
            Dimension d = cGroup.getSize(header.getTable());
            groupRect.width  = d.width;
            groupRect.height = d.height;    
            h.put(cGroup, groupRect);
          }
          paintCell(g, groupRect, cGroup);
          groupHeight += groupRect.height;
          cellRect.height = size.height - groupHeight;
          cellRect.y      = groupHeight;
        }
      }      
      cellRect.width = aColumn.getWidth() + columnMargin;
      if (cellRect.intersects(clipBounds)) {
        paintCell(g, cellRect, column);
      }
      cellRect.x += cellRect.width;
      column++;
    }
  }

  @SuppressWarnings("serial")
private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
    TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
    TableCellRenderer renderer = aColumn.getHeaderRenderer();
    //revised by Java2s.com
    renderer = new DefaultTableCellRenderer(){
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
          JLabel header = new JLabel();
            header.setForeground(table.getTableHeader().getForeground());
            header.setBackground(table.getTableHeader().getBackground());
            header.setFont(table.getTableHeader().getFont());

          header.setHorizontalAlignment(JLabel.CENTER);
          header.setText(value.toString());
          header.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            return header;
        }
    
    };
    Component c = renderer.getTableCellRendererComponent(
        header.getTable(), aColumn.getHeaderValue(),false, false, -1, columnIndex);
        
        c.setBackground(UIManager.getColor("control"));
      
    rendererPane.add(c);
    rendererPane.paintComponent(g, c, header, cellRect.x, cellRect.y,
        cellRect.width, cellRect.height, true);
  }

  private void paintCell(Graphics g, Rectangle cellRect,ColumnGroup cGroup) {
    TableCellRenderer renderer = cGroup.getHeaderRenderer();
      //revised by Java2s.com
     // if(renderer == null){
//      return ;
  //    }

    Component component = renderer.getTableCellRendererComponent(
      header.getTable(), cGroup.getHeaderValue(),false, false, -1, -1);
    rendererPane.add(component);
    rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
        cellRect.width, cellRect.height, true);
  }

  @SuppressWarnings("rawtypes")
private int getHeaderHeight() {
    int height = 0;
    TableColumnModel columnModel = header.getColumnModel();
    for(int column = 0; column < columnModel.getColumnCount(); column++) {
      TableColumn aColumn = columnModel.getColumn(column);
      TableCellRenderer renderer = aColumn.getHeaderRenderer();
      //revised by Java2s.com
      if(renderer == null){
      return 60;
      }
      
      Component comp = renderer.getTableCellRendererComponent(
        header.getTable(), aColumn.getHeaderValue(), false, false,-1, column);
      int cHeight = comp.getPreferredSize().height;
      Enumeration e = ((GroupableTableHeader)header).getColumnGroups(aColumn);      
      if (e != null) {
        while (e.hasMoreElements()) {
          ColumnGroup cGroup = (ColumnGroup)e.nextElement();
          cHeight += cGroup.getSize(header.getTable()).height;
        }
      }
      height = Math.max(height, cHeight);
    }
    return height;
  }

  private Dimension createHeaderSize(long width) {
    TableColumnModel columnModel = header.getColumnModel();
    width += columnModel.getColumnMargin() * columnModel.getColumnCount();
    if (width > Integer.MAX_VALUE) {
      width = Integer.MAX_VALUE;
    }
    return new Dimension((int)width, getHeaderHeight());
  }

  @SuppressWarnings("rawtypes")
public Dimension getPreferredSize(JComponent c) {
    long width = 0;
    Enumeration enumeration = header.getColumnModel().getColumns();
    while (enumeration.hasMoreElements()) {
      TableColumn aColumn = (TableColumn)enumeration.nextElement();
      width = width + aColumn.getPreferredWidth();
    }
    return createHeaderSize(width);
  }
}

/*
 * (swing1.1beta3)
 * 
 */



/**
  * ColumnGroup
  *
  * @version 1.0 10/20/98
  * @author Nobuo Tamemasa
  */
 
class ColumnGroup {
  protected TableCellRenderer renderer;
  @SuppressWarnings("rawtypes")
protected Vector v;
  protected String text;
  protected int margin=0;

  public ColumnGroup(String text) {
    this(null,text);
  }

  @SuppressWarnings({ "serial", "rawtypes" })
public ColumnGroup(TableCellRenderer renderer,String text) {
    if (renderer == null) {
      this.renderer = new DefaultTableCellRenderer() {
  public Component getTableCellRendererComponent(JTable table, Object value,
                         boolean isSelected, boolean hasFocus, int row, int column) {
    JTableHeader header = table.getTableHeader();
    if (header != null) {
      setForeground(header.getForeground());
      setBackground(header.getBackground());
      setFont(header.getFont());
    }
          setHorizontalAlignment(JLabel.CENTER);
          setText((value == null) ? "" : value.toString());
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    return this;
        }
      };
    } else {
      this.renderer = renderer;
    }
    this.text = text;
    v = new Vector();
  }

  
  /**
   * @param obj    TableColumn or ColumnGroup
   */
  @SuppressWarnings("unchecked")
public void add(Object obj) {
    if (obj == null) { return; }
    v.addElement(obj);
  }

  
  /**
   * @param c    TableColumn
   * @param v    ColumnGroups
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
public Vector getColumnGroups(TableColumn c, Vector g) {
    g.addElement(this);
    if (v.contains(c)) return g;    
    Enumeration e = v.elements();
    while (e.hasMoreElements()) {
      Object obj = e.nextElement();
      if (obj instanceof ColumnGroup) {
        Vector groups = 
          (Vector)((ColumnGroup)obj).getColumnGroups(c,(Vector)g.clone());
        if (groups != null) return groups;
      }
    }
    return null;
  }
    
  public TableCellRenderer getHeaderRenderer() {
    return renderer;
  }
    
  public void setHeaderRenderer(TableCellRenderer renderer) {
    if (renderer != null) {
      this.renderer = renderer;
    }
  }
    
  public Object getHeaderValue() {
    return text;
  }
  
  @SuppressWarnings("rawtypes")
public Dimension getSize(JTable table) {
    Component comp = renderer.getTableCellRendererComponent(
        table, getHeaderValue(), false, false,-1, -1);
    int height = comp.getPreferredSize().height; 
    int width  = 0;
    Enumeration e = v.elements();
    while (e.hasMoreElements()) {
      Object obj = e.nextElement();
      if (obj instanceof TableColumn) {
        TableColumn aColumn = (TableColumn)obj;
        width += aColumn.getWidth();
        width += margin;
      } else {
        width += ((ColumnGroup)obj).getSize(table).width;
      }
    }
    return new Dimension(width, height);
  }

  @SuppressWarnings("rawtypes")
public void setColumnMargin(int margin) {
    this.margin = margin;
    Enumeration e = v.elements();
    while (e.hasMoreElements()) {
      Object obj = e.nextElement();
      if (obj instanceof ColumnGroup) {
        ((ColumnGroup)obj).setColumnMargin(margin);
      }
    }
  }
}
