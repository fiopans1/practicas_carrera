package es.udc.ws.app.thriftservice;

import es.udc.ws.util.servlet.ThriftHttpServletTemplate;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import es.udc.ws.app.thrift.*;

public class ThriftEventoServiceServlet extends ThriftHttpServletTemplate {

    public ThriftEventoServiceServlet() {
        super(createProcessor(), createProtocolFactory());
    }

    private static TProcessor createProcessor() {
        return new ThriftEventoService.Processor<ThriftEventoService.Iface>(
                new ThriftEventoServiceImpl());
    }

    private static TProtocolFactory createProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }

}
