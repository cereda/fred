/**
 * Fred.
 */
package com.github.cereda.fred.utils;

import com.github.cereda.fred.model.PrintingModel;
import com.github.cereda.fred.model.PrintingOptions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

/**
 * Formatter.
 *
 * @author Paulo Roberto Massa Cereda
 */
public class Formatter {

    public void format(BibTeXEntry entry) throws FileNotFoundException {
        File config = new File("config.yaml");
        if (!config.exists()) {
            System.err.println("'config.yaml' was not found.");
            System.exit(1);
        }
        FileReader reader = new FileReader(config);
        Yaml yaml;
        PrintingOptions options = null;
        try {
            yaml = new Yaml(new Constructor(PrintingOptions.class), new Representer(), new DumperOptions());
            options = (PrintingOptions) yaml.load(reader);
        }
        catch (YAMLException yamlException) {
            System.out.println("An error occurred with the 'config.yaml' file.");
            System.exit(1);
        }
        PrintingModel model = options.getModel(entry.getType().getValue());
        if (model == null) {
            System.err.println("Model for '".concat(entry.getType().getValue()).concat("' was not found."));
        } else {
            RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
            StringReader templateReader = new StringReader(model.getFormat());
            SimpleNode node = null;
            try {
                node = runtimeServices.parse(templateReader, "foobar");
            } catch (ParseException pe) {
                System.err.println("Error parsing template for '".concat(entry.getType().getValue()).concat("'."));
                System.exit(1);
            }
            Template velocityTemplate = new Template();
            velocityTemplate.setRuntimeServices(runtimeServices);
            velocityTemplate.setData(node);
            velocityTemplate.initDocument();
            VelocityContext context = new VelocityContext();
            for (Object key : entry.getFields().keySet()) {
                context.put(key.toString(), entry.getField(new Key(key.toString())).toUserString());
            }
            try {
                StringWriter stringWriter = new StringWriter();
                velocityTemplate.merge(context, stringWriter);
                System.out.println(WordUtils.wrap(stringWriter.getBuffer().toString(), 65, "\n", true));
                stringWriter.close();
            } catch (IOException e) {
                System.err.println("Error in the template writer.");
                System.exit(1);
            }
        }
    }
}
