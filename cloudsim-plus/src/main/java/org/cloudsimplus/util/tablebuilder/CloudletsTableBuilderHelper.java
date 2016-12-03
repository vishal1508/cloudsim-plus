package org.cloudsimplus.util.tablebuilder;

import java.util.List;
import org.cloudbus.cloudsim.cloudlets.Cloudlet;
import org.cloudbus.cloudsim.datacenters.Datacenter;
import org.cloudbus.cloudsim.hosts.Host;
import org.cloudbus.cloudsim.vms.Vm;

/**
 * A class to help printing simulation results for a list of cloudlets.
 *
 * @author Manoel Campos da Silva Filho
 */
public class CloudletsTableBuilderHelper {
    private TableBuilder printer;
    private List<? extends Cloudlet> cloudletList;

    /**
     * Creates new helper object to print the list of cloudlets using the a
     * default {@link TextTableBuilder}.
     * To use a different {@link TableBuilder}, use the
     * {@link #setPrinter(TableBuilder)} method.
     *
     * @param list the list of Cloudlets that the data will be included into the table to be printed
     */
    public CloudletsTableBuilderHelper(final List<? extends Cloudlet> list){
        this.setPrinter(new TextTableBuilder()).setCloudletList(list);
    }

    public CloudletsTableBuilderHelper setTitle(String title){
        printer.setTitle(title);
        return this;
    }

    /**
     * Builds the table with the data of the Cloudlet list and shows the results.
     */
    public void build(){
        if(printer.getTitle().isEmpty()){
            printer.setTitle("SIMULATION RESULTS");
        }

        createTableColumns();
        cloudletList.stream().forEach(cloudlet -> addDataToRow(cloudlet, printer.newRow()));
        printer.print();
    }


    protected void createTableColumns() {
        printer.addColumn("Cloudlet").setSubTitle("ID");
        printer.addColumn("Status ");
        printer.addColumn("DC").setSubTitle("ID");
        printer.addColumn("Host").setSubTitle("ID");
        printer.addColumn("VM").setSubTitle("ID");
        printer.addColumn("CloudletLen").setSubTitle("MI");
        printer.addColumn("CloudletPEs").setSubTitle("CPU cores");
        printer.addColumn("StartTime").setFormat("%d").setSubTitle("Seconds");
        printer.addColumn("FinishTime").setFormat("%d").setSubTitle("Seconds");
        printer.addColumn("ExecTime").setFormat("%.0f").setSubTitle("Seconds");
    }

    /**
     * Add data to a row of the table being generated.
     * @param cloudlet The cloudlet to get to data to show in the row of the table
     * @param row The row to be added the data to
     */
    protected void addDataToRow(Cloudlet cloudlet, List<Object> row) {
        Vm vm = cloudlet.getVm();
        Host host = vm.getHost();
        Datacenter datacenter = host.getDatacenter();

        row.add(cloudlet.getId());
        row.add(cloudlet.getStatus().name());
        row.add(datacenter.getId());
        row.add(host.getId());
        row.add(vm.getId());
        row.add(cloudlet.getCloudletLength());
        row.add(cloudlet.getNumberOfPes());
        row.add((int)cloudlet.getExecStartTime());
        row.add((int)cloudlet.getFinishTime());
        row.add(cloudlet.getActualCPUTime());
    }

    public final CloudletsTableBuilderHelper setPrinter(TableBuilder printer) {
        this.printer = printer;
        return this;
    }

    protected CloudletsTableBuilderHelper setCloudletList(List<? extends Cloudlet> cloudletList) {
        this.cloudletList = cloudletList;
        return this;
    }

    protected TableBuilder getPrinter() {
        return printer;
    }
}
